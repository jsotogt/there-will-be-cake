package cake.machinery;

/**
 * Represents the valid classes for a given id based on:
 *     (1) IDs that are evenly divisible by 3 are priority IDs.
 *     (2) IDs that are evenly divisible by 5 are VIP IDs.
 *     (3) IDs that are evenly divisible by both 3 and 5 are management override.
 *     (4) IDs that are not evenly divisible by 3 or 5 are normal IDs.
 */
public enum ClassId {

    PRIORITY, VIP, OVERRIDE, NORMAL

}
