package es.icp.icp_commons.Interfaces;

import es.icp.icp_commons.Objects.ImagenCommons;

public interface AdjuntarImagenesListener {

    void imagenAdjuntada(ImagenCommons imagen);

    void imagenEliminada(int position, ImagenCommons imagen);

}
