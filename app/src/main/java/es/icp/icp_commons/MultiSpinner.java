package es.icp.icp_commons;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;

import java.util.List;

import es.icp.icp_commons.Interfaces.MultiSpinnerListener;

@SuppressWarnings("unused")
public class MultiSpinner extends AppCompatSpinner implements
        DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {

    private List<String>         items;
    private boolean[]            selected;
    private String               defaultText;
    private MultiSpinnerListener listener;
    private Context              context;
    private boolean              enableMulti;
    private boolean              modificado = false;

    /**
     * Constructor MultiSpinner de 1 parámetro
     *
     * @param context Context. Contexto de la aplicación.
     * @author Ventura de Lucas
     */
    public MultiSpinner(Context context) {
        super(context);
        this.context = context;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[]{""});
        setAdapter(adapter);
    }

    /**
     * Constructor MultiSpinner de 2 parámetros.
     * Se recomienda su uso exclusivo para los ficheros layout xml.
     *
     * @param context Context. Contexto de la aplicación.
     * @param attrs   AttributeSet. Atributos recibidos desde el fichero layout xml.
     * @author Ventura de Lucas
     */
    public MultiSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiSpinner);
        this.enableMulti = typedArray.getBoolean(R.styleable.MultiSpinner_enableMulti, true);
        typedArray.recycle();
        this.context = context;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[]{""});
        setAdapter(adapter);
    }

    /**
     * Constructor MultiSpinner de 3 parámetros.
     * Se recomienda su uso exclusivo para los ficheros layout xml.
     *
     * @param arg0 Context. Contexto de la aplicación.
     * @param arg1 AttributeSet. Atributos recibidos desde el fichero layout xml.
     * @param arg2 int. Atributos recibidos desde el fichero layout xml.
     * @author Ventura de Lucas
     */
    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (!this.enableMulti && isChecked) {
            ListView listView = ((AlertDialog) dialog).getListView();
            for (int i = 0; i < selected.length; i++) {
                selected[i] = false;
                listView.setItemChecked(i, false);
            }
        }
        selected[which] = isChecked;
                          modificado = true;
    }

    /**
     * Obtiene los items seleccionados.
     *
     * @return Devuelve un array de 'boolean' con los valores de cada item. ('true': seleccionado; 'false': no seleccionado)
     * @author Ventura de Lucas
     */
    public boolean[] getSelected() {
        return selected;
    }

    /**
     * Modifica los items seleccionados.
     *
     * @param selected boolean[]. Array de 'boolean' con los valores de cada item. ('true': seleccionado; 'false': no seleccionado)
     * @author Ventura de Lucas
     */
    public void setSelected(boolean[] selected) {
        this.selected = selected;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // refresh text on spinner
        if (!modificado) return;
        StringBuilder spinnerBuffer  = new StringBuilder();
        boolean       someUnselected = false;
        for (int i = 0; i < items.size(); i++) {
            if (selected[i]) {
                spinnerBuffer.append(items.get(i));
                spinnerBuffer.append(", ");
            } else {
                someUnselected = true;
            }
        }
        String spinnerText;
        if (someUnselected) {
            spinnerText = spinnerBuffer.toString();
            if (spinnerText.length() > 2)
                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
        } else {
            spinnerText = defaultText;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[]{spinnerText});
        setAdapter(adapter);
        listener.onItemsSelected(selected);
        modificado = false;
    }

    /**
     * Sirve para rellenar el TextView con una suma o concatenación de los 'String' de cada item.
     *
     * @author Ventura de Lucas
     */
    public void rellenarMultiSpinner() {
        StringBuilder spinnerBuffer  = new StringBuilder();
        boolean       someUnselected = false;
        for (int i = 0; i < items.size(); i++) {
            if (selected[i]) {
                spinnerBuffer.append(items.get(i));
                spinnerBuffer.append(", ");
            } else {
                someUnselected = true;
            }
        }
        String spinnerText;
        if (someUnselected) {
            spinnerText = spinnerBuffer.toString();
            if (spinnerText.length() > 2)
                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
        } else {
            spinnerText = defaultText;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[]{spinnerText});
        setAdapter(adapter);
        listener.onItemsSelected(selected);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean performClick() {
        if (items == null || items.size() == 0) {
            Toast.makeText(context, context.getString(R.string.multi_spinner_sin_informacion), Toast.LENGTH_SHORT).show();
            return false;
        }
        int                 size    = items.size();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(
                items.toArray(new CharSequence[size]), selected, this);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }

    /**
     * Modifica la lista de items.
     *
     * @param items    List<String>. Listado de items.
     * @param allText  String. Texto por defecto del TextView.
     * @param listener MultiSpinnerListener. Para detectar la modificación de los items seleccionados al cerrar el diálogo del MultiSpinner.
     * @author Ventura de Lucas
     */
    public void setItems(List<String> items, String allText,
                         MultiSpinnerListener listener) {
        this.items       = items;
        this.defaultText = allText;
        this.listener    = listener;

        // all selected by default
        selected = new boolean[items.size()];
        for (int i = 0; i < selected.length; i++)
             selected[i] = false;

        // all text on the spinner
        if (this.enableMulti) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, new String[]{allText});
            setAdapter(adapter);
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, new String[]{context.getString(R.string.multi_spinner_seleccione)});
            setAdapter(adapter);
        }
    }
}