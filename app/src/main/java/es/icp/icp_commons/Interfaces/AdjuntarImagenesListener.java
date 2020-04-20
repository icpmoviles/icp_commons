package es.icp.icp_commons.Interfaces;

import java.util.ArrayList;

import es.icp.icp_commons.Objects.ImagenCommons;

public interface AdjuntarImagenesListener {

    void imagenAdjuntada(ImagenCommons imagen);

    void imagenEliminada(int position, ImagenCommons imagen);

    void aceptar(ArrayList<ImagenCommons> imagenes);

}
