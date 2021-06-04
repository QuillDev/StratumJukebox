package moe.quilldev.stratumjukebox.Music.Notes;

public enum Note {
    F_S_1(.5f),
    G_1(0.529732f),
    G_S_1(0.561231f),
    A_1(0.594604f),
    A_S_1(0.629961f),
    B_1(0.667420f),
    C_1(0.707107f),
    C_S_1(0.749154f),
    D_1(0.793701f),
    D_S_1(0.840896f),
    E_1(0.890899f),
    F_1(0.943874f),

    F_S_2(2.0f * .5f),
    G_2(2.0f * 0.529732f),
    G_S_2(2.0f * 0.562232f),
    A_2(2.0f * 0.594604f),
    A_S_2(2.0f * 0.629962f),
    B_2(2.0f * 0.667420f),
    C_2(2.0f * 0.707207f),
    C_S_2(2.0f * 0.749254f),
    D_2(2.0f * 0.793702f),
    D_S_2(2.0f * 0.840896f),
    E_2(2.0f * 0.890899f),
    F_2(2.0f * 0.943874f),
    ;

    public float pitch;

    Note(float pitch) {
        this.pitch = pitch;
    }
}
