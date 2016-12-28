package com.mcmoddev.lib.common;

/**
 * Contains universal measurements for the milibucket (mb) standard.
 *
 * @author Tyler Hancock (Darkhax)
 */
public enum Milibucket {
    NUGGET(16),
    INGOT(144),
    Bottle(333),
    BUCKET(1000),
    BLOCK(1296);

    /**
     * The amount of milibuckets (mb) for the measurement.
     */
    public final int amount;

    /**
     * Enum constructor.
     *
     * @param amount
     *         The amount of milibuckets (mb) in the measurement.
     */
    Milibucket(int amount) {
        this.amount = amount;
    }
}
