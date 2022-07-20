package es.icp.icp_commons.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.icp.icp_commons.databinding.CustomSmoothDialogBinding

class CustomSmoothDialog : Fragment() {

    companion object {
        fun fragment() = CustomSmoothDialog()
    }

    private var _binding: CustomSmoothDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Binding
//        _binding = CustomSmoothDialogBinding.inflate(inflater, requireActivity().window.decorView.findViewById(android.R.id.content), false)
        _binding = CustomSmoothDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}