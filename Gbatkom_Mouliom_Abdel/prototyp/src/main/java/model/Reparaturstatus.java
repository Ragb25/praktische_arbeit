package model;

/**
 * Model-Klasse: Bearbeitungsstand eines Reparaturauftrags.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public enum Reparaturstatus {

    /** Auftrag wurde angelegt, aber noch nicht bearbeitet. */
    ANGELEGT,

    /** Auftrag wird gerade bearbeitet. */
    IN_BEARBEITUNG,

    /** Auftrag ist abgeschlossen. */
    ABGESCHLOSSEN
}
