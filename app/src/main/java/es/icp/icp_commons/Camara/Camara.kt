package es.icp.icp_commons.Camara


import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.display.DisplayManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import es.icp.icp_commons.Helpers.Constantes
import es.icp.icp_commons.R
import es.icp.icp_commons.Utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


typealias LumaListener = (luma: Double) -> Unit

class Camara : AppCompatActivity() {

    val KEY_EVENT_ACTION = "key_event_action"
    val KEY_EVENT_EXTRA = "key_event_extra"
    val EXTENSION_WHITELIST = arrayOf("JPG")
    val ANIMATION_FAST_MILLIS = 50L
    val ANIMATION_SLOW_MILLIS = 100L

    private lateinit var container: ConstraintLayout
    private lateinit var viewFinder: PreviewView
    private lateinit var outputDirectory: File
    private lateinit var broadcastManager: LocalBroadcastManager

    private var displayId: Int = -1
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var size: Size? = null
    private lateinit var view : ConstraintLayout
    private lateinit var btnFlash : ImageButton

    private var record : Boolean = true
    private var encenderFlash : Boolean = true
    private var cameraFront: Boolean = false
    private var cameraBack: Boolean = false
    private var gallery: Boolean = false

    var showAnimation = false

    private lateinit var videoCapture: VideoCapture
    private var video: Boolean = false
    private var outputVideoDirectory: File? = null

    private lateinit var chronometer: Chronometer

    private val displayManager by lazy {
        this.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }
    /** Blocking camera operations are performed using this executor */
    private lateinit var cameraExecutor: ExecutorService

    /** Volume down button receiver used to trigger shutter */
    private val volumeDownReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getIntExtra(KEY_EVENT_EXTRA, KeyEvent.KEYCODE_UNKNOWN)) {
                // When the volume down button is pressed, simulate a shutter button click
                KeyEvent.KEYCODE_VOLUME_DOWN -> {
                    val shutter = container
                        .findViewById<ImageButton>(R.id.camera_capture_button)
                    shutter.simulateClick()
                }
            }
        }
    }

    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit
        override fun onDisplayChanged(displayId: Int) = view?.let { view ->
            try {
                if (displayId == displayId) {
                    Log.d(TAG, "Rotation changed: ${view.display.rotation}")
                    imageCapture?.targetRotation = view.display.rotation
                    imageAnalyzer?.targetRotation = view.display.rotation
                }
            } catch (e: Exception) {
                Log.e(TAG, "Use case binding failed", e)
                e.printStackTrace()
            }
        } ?: Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camara)

        getExtras()

//        setupView()
    }

    override fun onPostResume() {
        super.onPostResume()

        setupView()
    }

    private fun getExtras() {
        try {
            cameraFront = intent.extras!![CAMERA_FRONT] as Boolean
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        try {
            cameraBack = intent.extras!![CAMERA_BACK] as Boolean
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        try {
            video = intent.extras!![VIDEO] as Boolean
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        try {
            gallery = intent.extras!![GALLERY] as Boolean
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (PackageManager.PERMISSION_GRANTED != grantResults.firstOrNull()) {
                Toast.makeText(this, "Sin permisos", Toast.LENGTH_LONG).show()
                this.finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Shut down our background executor
        cameraExecutor.shutdown()

        // Unregister the broadcast receivers and listeners
        broadcastManager.unregisterReceiver(volumeDownReceiver)
        displayManager.unregisterDisplayListener(displayListener)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


    companion object {

        private const val TAG = "CameraXBasic"
        const val CAMERA_FRONT = "CAMERA_FRONT"
        const val CAMERA_BACK = "CAMERA_BACK"
        const val VIDEO = "video"
        const val GALLERY = "gallery"
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_EXTENSION = ".jpg"
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0

        /** Helper function used to create a timestamped file */
        private fun createFile(baseFolder: File, format: String, extension: String) =
            File(baseFolder, SimpleDateFormat(format, Locale.US)
                .format(System.currentTimeMillis()) + extension)

        fun getOutputDirectory(context: Context): File {
            val appContext = context.applicationContext
            val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
                File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() } }
            return if (mediaDir != null && mediaDir.exists())
                mediaDir else appContext.filesDir
        }
    }

    private fun setGalleryThumbnail(uri: Uri) {
        if (gallery) {
            // Reference of the view that holds the gallery thumbnail
            val thumbnail = container.findViewById<ImageButton>(R.id.photo_view_button)

            // Run the operations in the view's thread
            thumbnail.post {

                // Remove thumbnail padding
                thumbnail.setPadding(resources.getDimension(R.dimen.stroke_small).toInt())

                try {
                    // Load thumbnail into circular button using Glide
                    Glide.with(thumbnail)
                        .load(uri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(thumbnail)
                } catch (exception: IllegalArgumentException) {
                    exception.printStackTrace()
                }
            }
        }
    }

    private fun setupView() {

        val permisos = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
        if (!Utils.comprobarPermisos(this, permisos)) {
            ActivityCompat.requestPermissions(this, permisos, 1)
        }


        view = findViewById(R.id.mainView)
        container = view as ConstraintLayout
        viewFinder = container.findViewById(R.id.view_finder)

        // Initialize our background executor
        cameraExecutor = Executors.newSingleThreadExecutor()

        broadcastManager = LocalBroadcastManager.getInstance(this)

        // Set up the intent filter that will receive events from our main activity
        val filter = IntentFilter().apply { addAction(KEY_EVENT_ACTION) }
        broadcastManager.registerReceiver(volumeDownReceiver, filter)

        // Every time the orientation of device changes, update rotation for use cases
        displayManager.registerDisplayListener(displayListener, null)

        // Determine the output directory
        outputDirectory = getOutputDirectory(this)

        // Wait for the views to be properly laid out
        viewFinder.post {

            // Keep track of the display in which this view is attached
            displayId = viewFinder.display.displayId

            // Build UI controls
            updateCameraUi()

            // Set up the camera and its use cases
            setUpCamera()
        }
    }

    /**
     * Inflate camera controls and update the UI manually upon config changes to avoid removing
     * and re-adding the view finder from the view hierarchy; this provides a seamless rotation
     * transition on devices that support it.
     *
     * NOTE: The flag is supported starting in Android 8 but there still is a small flash on the
     * screen for devices that run Android 9 or below.
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Rebind the camera with the updated display metrics
        bindCameraUseCases()

        // Enable or disable switching between cameras
        updateCameraSwitchButton()
    }

    /** Initialize CameraX, and prepare to bind the camera use cases  */
    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {

            // CameraProvider
            cameraProvider = cameraProviderFuture.get()

            // Select lensFacing depending on the available cameras
            lensFacing = when {
                hasBackCamera() -> CameraSelector.LENS_FACING_BACK
                hasFrontCamera() -> CameraSelector.LENS_FACING_FRONT
                else -> throw IllegalStateException("Back and front camera are unavailable")
            }

            // Enable or disable switching between cameras
            updateCameraSwitchButton()

            setUpZoomAndFocus()

            // Build and bind the camera use cases
            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(this))

    }

    private fun setUpZoomAndFocus() {
        showAnimation = true

        val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val scale = camera!!.cameraInfo.zoomState.value!!.zoomRatio * detector.scaleFactor
                camera!!.cameraControl.setZoomRatio(scale)
                showAnimation = false
                return true
            }
        }

        val scaleGestureDetector = ScaleGestureDetector(this, listener)

        viewFinder.setOnTouchListener { v, event ->
            scaleGestureDetector.onTouchEvent(event)
//            return@setOnTouchListener true

            if (event.action == MotionEvent.ACTION_DOWN) {
                showAnimation = true
                return@setOnTouchListener true
            }
            if (event.action == MotionEvent.ACTION_UP) {
                if (showAnimation) {
                    val factory = viewFinder.getMeteringPointFactory()

                    // Create a MeteringPoint from the tap coordinates
                    val point = factory.createPoint(event.x, event.y)

                    // Create a MeteringAction from the MeteringPoint, you can configure it to specify the metering mode
                    val action = FocusMeteringAction.Builder(point).build()

                    // Trigger the focus and metering. The method returns a ListenableFuture since the operation
                    // is asynchronous. You can use it get notified when the focus is successful or if it fails.
                    camera?.cameraControl?.startFocusAndMetering(action)

                    val iv = ImageView(this)
                    iv.layoutParams = LinearLayout.LayoutParams(200,200)
                    iv.setImageDrawable(getDrawable(R.drawable.ic_camara_focus))
                    iv.x = event.x
                    iv.y = event.y
                    iv.visibility = View.GONE
                    view.addView(iv)
                    var animation = AnimationUtils.loadAnimation(this, R.anim.alpha_aparicion)
                    animation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {
                            iv.visibility = View.VISIBLE
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            iv.visibility = View.GONE
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                            TODO("Not yet implemented")
                        }

                    })
                    //var animation2 = AnimationUtils.loadAnimation(this, R.anim.alpha_desaparicion)
                    iv.startAnimation(animation)
                }

            }
            true
        }
    }

    /** Declare and bind preview, capture and analysis use casess */
    private fun bindCameraUseCases() {

        try {
            val displayMetrics = DisplayMetrics()
            // Get screen metrics used to setup camera for full screen resolution
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            Log.d(TAG, "Screen metrics: ${displayMetrics.widthPixels} x ${displayMetrics.heightPixels}")

            val screenAspectRatio = aspectRatio(displayMetrics.widthPixels, displayMetrics.heightPixels)
            Log.d(TAG, "Preview aspect ratio: $screenAspectRatio")

            val rotation = viewFinder.display.rotation

            // CameraProvider
            val cameraProvider = cameraProvider
                ?: throw IllegalStateException("Camera initialization failed.")

            // CameraSelector
            if (cameraFront) lensFacing = CameraSelector.LENS_FACING_FRONT
            if (cameraBack) lensFacing = CameraSelector.LENS_FACING_BACK
            val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

            // Preview
            preview = Preview.Builder()
                // We request aspect ratio but no resolution
                .setTargetAspectRatio(screenAspectRatio)
//            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
//            .setTargetResolution(size!!)
                // Set initial target rotation
                .setTargetRotation(rotation)
                .build()

            // ImageCapture
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)

                // We request aspect ratio but no resolution to match preview config, but letting
                // CameraX optimize for whatever specific resolution best fits our use cases
//                .setTargetAspectRatio(screenAspectRatio)
//            .setTargetResolution(size!!)
                .setTargetResolution(Size(1080,1920))
//            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                // Set initial target rotation, we will have to call this again if rotation changes
                // during the lifecycle of this use case
                .setTargetRotation(rotation)
                .build()

            if(video) {
                videoCapture = VideoCapture.Builder().build()
                outputVideoDirectory = getOutputDirectory()
            }

            // ImageAnalysis
            imageAnalyzer = ImageAnalysis.Builder()
                // We request aspect ratio but no resolution
                .setTargetAspectRatio(screenAspectRatio)
                // Set initial target rotation, we will have to call this again if rotation changes
                // during the lifecycle of this use case
                .setTargetRotation(rotation)
//            .setTargetResolution(size!!)
                .build()
                // The analyzer can then be assigned to the instance
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                        // Values returned from our analyzer are passed to the attached listener
                        // We log image analysis results here - you should do something useful
                        // instead!
                        Log.d(TAG, "Average luminosity: $luma")
                    })
                }

            // Must unbind the use-cases before rebinding them
            cameraProvider.unbindAll()

            // A variable number of use-cases can be passed here -
            // camera provides access to CameraControl & CameraInfo

            camera = if(video){
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, videoCapture)
            } else {
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalyzer)
            }

            // Attach the viewfinder's surface provider to preview use case
            preview?.setSurfaceProvider(viewFinder.surfaceProvider)
        } catch (exc: Exception) {
            Log.e(TAG, "Use case binding failed", exc)
            exc.printStackTrace()
        }
    }

    /**
     *  [androidx.camera.core.ImageAnalysisConfig] requires enum value of
     *  [androidx.camera.core.AspectRatio]. Currently it has values of 4:3 & 16:9.
     *
     *  Detecting the most suitable ratio for dimensions provided in @params by counting absolute
     *  of preview ratio to one of the provided values.
     *
     *  @param width - preview width
     *  @param height - preview height
     *  @return suitable aspect ratio
     */
    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    /** Method used to re-draw the camera UI controls, called every time configuration changes. */
    private fun updateCameraUi() {

        // Remove previous UI if any
        container.findViewById<ConstraintLayout>(R.id.camera_ui_container)?.let {
            container.removeView(it)
        }

        // Inflate a new view containing all UI for controlling the camera
        val controls = View.inflate(this, R.layout.camera_ui_container, container)

        chronometer = controls.findViewById(R.id.c_meter)

        btnFlash = controls.findViewById(R.id.flash_button)

        btnFlash.setOnClickListener {
            if (encenderFlash) {
                camera!!.cameraControl.enableTorch(true)
                btnFlash.setImageDrawable(getDrawable(R.drawable.ic_flash))
            } else {
                camera!!.cameraControl.enableTorch(false)
                btnFlash.setImageDrawable(getDrawable(R.drawable.ic_flash_off))
            }

            encenderFlash = !encenderFlash
        }

        // In the background, load latest photo taken (if any) for gallery thumbnail
        lifecycleScope.launch(Dispatchers.IO) {
            outputDirectory.listFiles { file ->
                EXTENSION_WHITELIST.contains(file.extension.toUpperCase(Locale.ROOT))
            }?.maxOrNull()?.let {
                setGalleryThumbnail(Uri.fromFile(it))
            }
        }

        if(video) {
            chronometer.isVisible = true

            controls.findViewById<ImageButton>(R.id.camera_capture_button).setOnClickListener {
                if (record) {
                    startRecording()
                    chronometer.base = SystemClock.elapsedRealtime()
                    chronometer.start()
                    controls.findViewById<ImageButton>(R.id.camera_capture_button).setImageDrawable(getDrawable(R.drawable.ic_stop))
                } else {
                    stopRecording()
                    chronometer.stop()
                    controls.findViewById<ImageButton>(R.id.camera_capture_button).setImageDrawable(getDrawable(R.drawable.ic_shutter))
                }

                record = !record
            }
        } else {
            controls.findViewById<ImageButton>(R.id.camera_capture_button).setOnClickListener {

                // Get a stable reference of the modifiable image capture use case
                imageCapture?.let { imageCapture ->

                    // Create output file to hold the image
                    val photoFile = createFile(outputDirectory, FILENAME, PHOTO_EXTENSION)

                    // Setup image capture metadata
                    val metadata = ImageCapture.Metadata().apply {

                        // Mirror image when using the front camera
                        isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
                    }

                    // Create output options object which contains file + metadata
                    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
                        .setMetadata(metadata)
                        .build()

                    // Setup image capture listener which is triggered after photo has been taken
                    imageCapture.takePicture(
                        outputOptions, cameraExecutor, object : ImageCapture.OnImageSavedCallback {
                            override fun onError(exc: ImageCaptureException) {
                                Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                            }

                            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                                Log.d(TAG, "Photo capture succeeded: $savedUri")

                                // We can only change the foreground Drawable using API level 23+ API
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    // Update the gallery thumbnail with latest picture taken
                                    setGalleryThumbnail(savedUri)
                                }

                                // Implicit broadcasts will be ignored for devices running API level >= 24
                                // so if you only target API level 24+ you can remove this statement
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                                    this@Camara.sendBroadcast(
                                        Intent(android.hardware.Camera.ACTION_NEW_PICTURE, savedUri)
                                    )
                                }

                                // If the folder selected is an external media directory, this is
                                // unnecessary but otherwise other apps will not be able to access our
                                // images unless we scan them using [MediaScannerConnection]
                                val mimeType = MimeTypeMap.getSingleton()
                                    .getMimeTypeFromExtension(savedUri.toFile().extension)
                                MediaScannerConnection.scanFile(
                                    this@Camara,
                                    arrayOf(savedUri.toFile().absolutePath),
                                    arrayOf(mimeType)
                                ) { _, uri ->
                                    Log.d(TAG, "Image capture scanned into media store: $uri")
                                }

                                val intent = this@Camara.intent
                                intent.putExtra(Constantes.INTENT_CAMARAX, savedUri.toString())
                                setResult(RESULT_OK, intent)
                                finish()

                            }
                        })

                    // We can only change the foreground Drawable using API level 23+ API
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        // Display flash animation to indicate that photo was captured
                        container.postDelayed({
                            container.foreground = ColorDrawable(Color.WHITE)
                            container.postDelayed(
                                { container.foreground = null }, ANIMATION_FAST_MILLIS)
                        }, ANIMATION_SLOW_MILLIS)
                    }
                }
            }
        }

        // Listener for button used to capture photo


        // Setup for button used to switch cameras
        controls.findViewById<ImageButton>(R.id.camera_switch_button).let {

            // Disable the button until the camera is set up
            it.isEnabled = false

            // Listener for button used to switch cameras. Only called if the button is enabled
            it.setOnClickListener {
                lensFacing = if (CameraSelector.LENS_FACING_FRONT == lensFacing) {
                    CameraSelector.LENS_FACING_BACK
                } else {
                    CameraSelector.LENS_FACING_FRONT
                }
                // Re-bind use cases to update selected camera
                bindCameraUseCases()
            }
        }

        if(gallery) {
            controls.findViewById<ImageButton>(R.id.photo_view_button).isVisible = true

            // Listener for button used to view the most recent photo
            controls.findViewById<ImageButton>(R.id.photo_view_button).setOnClickListener {
                // Only navigate when the gallery has photos
//            if (true == outputDirectory.listFiles()?.isNotEmpty()) {
//                Navigation.findNavController(
//                    this, R.id.fragment_container
//                ).navigate(CameraFragmentDirections
//                    .actionCameraToGallery(outputDirectory.absolutePath))
//            }
                var galeryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                if(video) {
                    galeryIntent.type = "video/*"
                } else {
                    galeryIntent.type = "image/*"
                }
                startActivityForResult(galeryIntent, Constantes.INTENT_CAMARA)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK && requestCode == Constantes.INTENT_CAMARA) {
            var photoFile = createFileFromURI(data!!.data!!)

            val intent = this@Camara.intent
            intent.putExtra(Constantes.INTENT_CAMARAX, photoFile.absolutePath)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    /** Enabled or disabled a button to switch cameras depending on the available cameras */
    private fun updateCameraSwitchButton() {
        val switchCamerasButton = container.findViewById<ImageButton>(R.id.camera_switch_button)
        try {
            if (cameraBack || cameraFront) {
                switchCamerasButton.isEnabled = false
                switchCamerasButton.visibility = View.GONE
            }
            else switchCamerasButton.isEnabled = hasBackCamera() && hasFrontCamera()
        } catch (exception: Exception/*CameraInfoUnavailableException*/) {
            switchCamerasButton.isEnabled = false
        }
    }

    /** Returns true if the device has an available back camera. False otherwise */
    private fun hasBackCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) ?: false
    }

    /** Returns true if the device has an available front camera. False otherwise */
    private fun hasFrontCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) ?: false
    }

    /**
     * Our custom image analysis class.
     *
     * <p>All we need to do is override the function `analyze` with our desired operations. Here,
     * we compute the average luminosity of the image by looking at the Y plane of the YUV frame.
     */
    private class LuminosityAnalyzer(listener: LumaListener? = null) : ImageAnalysis.Analyzer {
        private val frameRateWindow = 8
        private val frameTimestamps = ArrayDeque<Long>(5)
        private val listeners = ArrayList<LumaListener>().apply { listener?.let { add(it) } }
        private var lastAnalyzedTimestamp = 0L
        var framesPerSecond: Double = -1.0
            private set

        /**
         * Used to add listeners that will be called with each luma computed
         */
        fun onFrameAnalyzed(listener: LumaListener) = listeners.add(listener)

        /**
         * Helper extension function used to extract a byte array from an image plane buffer
         */
        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        /**
         * Analyzes an image to produce a result.
         *
         * <p>The caller is responsible for ensuring this analysis method can be executed quickly
         * enough to prevent stalls in the image acquisition pipeline. Otherwise, newly available
         * images will not be acquired and analyzed.
         *
         * <p>The image passed to this method becomes invalid after this method returns. The caller
         * should not store external references to this image, as these references will become
         * invalid.
         *
         * @param image image being analyzed VERY IMPORTANT: Analyzer method implementation must
         * call image.close() on received images when finished using them. Otherwise, new images
         * may not be received or the camera may stall, depending on back pressure setting.
         *
         */
        override fun analyze(image: ImageProxy) {
            // If there are no listeners attached, we don't need to perform analysis
            if (listeners.isEmpty()) {
                image.close()
                return
            }

            // Keep track of frames analyzed
            val currentTime = System.currentTimeMillis()
            frameTimestamps.push(currentTime)

            // Compute the FPS using a moving average
            while (frameTimestamps.size >= frameRateWindow) frameTimestamps.removeLast()
            val timestampFirst = frameTimestamps.peekFirst() ?: currentTime
            val timestampLast = frameTimestamps.peekLast() ?: currentTime
            framesPerSecond = 1.0 / ((timestampFirst - timestampLast) /
                    frameTimestamps.size.coerceAtLeast(1).toDouble()) * 1000.0


            // Analysis could take an arbitrarily long amount of time
            // Since we are running in a different thread, it won't stall other use cases

            lastAnalyzedTimestamp = frameTimestamps.first

            // Since format in ImageAnalysis is YUV, image.planes[0] contains the luminance plane
            val buffer = image.planes[0].buffer

            // Extract image data from callback object
            val data = buffer.toByteArray()

            // Convert the data into an array of pixel values ranging 0-255
            val pixels = data.map { it.toInt() and 0xFF }

            // Compute average luminance for the image
            val luma = pixels.average()

            // Call all listeners with new value
            listeners.forEach { it(luma) }

            image.close()
        }
    }

    fun ImageButton.simulateClick(delay: Long = ANIMATION_FAST_MILLIS) {
        performClick()
        isPressed = true
        invalidate()
        postDelayed({
            invalidate()
            isPressed = false
        }, delay)
    }

    fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    @SuppressLint("RestrictedApi")
    private fun startRecording() {
        val videoFile = File(
            outputDirectory,
            SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US
            ).format(System.currentTimeMillis()) + ".mp4")

        val outputOptions = VideoCapture.OutputFileOptions.Builder(videoFile).build()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
        ) { return }
        videoCapture?.startRecording(outputOptions, ContextCompat.getMainExecutor(this), object: VideoCapture.OnVideoSavedCallback {
            override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
                Log.e(TAG, "Video capture failed: $message")
            }

            override fun onVideoSaved(outputFileResults: VideoCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(videoFile)
                val msg = "Video capture succeeded: $savedUri"

                val intent = this@Camara.intent
                intent.putExtra(Constantes.INTENT_CAMARAX, savedUri.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
        })
    }

    @SuppressLint("RestrictedApi")
    private fun stopRecording() {
        videoCapture?.stopRecording()
    }

    private fun createFileFromURI(uri: Uri) : File {
        val photoFile = createFile(outputDirectory, FILENAME, PHOTO_EXTENSION)
        var inputStream = applicationContext.contentResolver.openInputStream(uri)

        var out: FileOutputStream? = null

        try {
            out = FileOutputStream(photoFile)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        if(inputStream != null) {
            var count = 0
            val buffer = ByteArray(1024 * 4)
            val eof = -1
            var n = 0
            while(eof != (inputStream.read(buffer).also { n = it })) {
                out!!.write(buffer, 0, n)
                count += n
            }

            inputStream.close()
        }

        out?.close()

        return photoFile
    }

}