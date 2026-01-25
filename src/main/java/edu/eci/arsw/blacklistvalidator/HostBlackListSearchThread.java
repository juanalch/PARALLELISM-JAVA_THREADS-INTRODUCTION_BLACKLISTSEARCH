package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.*;
import java.util.*;

/**
 * Thread that performs parallel search of an IP address in a segment
 * of available blacklists.
 *
 * This thread is responsible for verifying whether a specific IP address is
 * registered in a particular range of blacklist servers. It is part of a
 * parallelization scheme where multiple instances of this class search in
 * different segments simultaneously to accelerate the validation process.
 *
 * Uses the facade (HostBlacklistsDataSourceFacade) in a Thread-Safe manner,
 * allowing multiple threads to access the same data source without conflicts.
 *
 * @author Anderson Fabian Garcia Nieto
 * @author Juana Lozano Chaves
 */
public class HostBlackListSearchThread extends Thread {

    private final int startIndex;
    private final int endIndex;
    private final String ipaddress;
    private final HostBlacklistsDataSourceFacade facade;
    private final List<Integer> occurrences = new LinkedList<>();
    private int checkedLists = 0;

    /**
     * Creates a new blacklist search thread.
     *
     * @param startIndex    starting index (inclusive) of the range to search
     * @param endIndex      ending index (exclusive) of the range to search
     * @param ipaddress     IP address to validate (e.g., "200.24.34.55")
     * @param facade        instance of the blacklist data source facade
     */
    public HostBlackListSearchThread(int startIndex, int endIndex, String ipaddress, 
                                     HostBlacklistsDataSourceFacade facade) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.ipaddress = ipaddress;
        this.facade = facade;
    }

    /**
     * Executes the IP address search in the assigned blacklist segment.
     *
     * Iterates over all blacklists in the range [startIndex, endIndex) and verifies
     * whether the IP is registered in each one. Records the number of each blacklist
     * where it is found and maintains a counter of the total number of blacklists reviewed.
     *
     * This method is automatically called when the thread is started with start().
     */
    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            checkedLists++;
            if (facade.isInBlackListServer(i, ipaddress)) {
                occurrences.add(i);
            }
        }
    }

    /**
     * Returns the total number of blacklists this thread reviewed.
     *
     * @return number of blacklists queried in the assigned range
     */
    public int getCheckedLists() {
        return checkedLists;
    }

    /**
     * Returns the list of blacklist numbers where the IP address was found.
     *
     * @return List containing the indices of blacklists that contain the IP.
     *         The list is empty if the IP was not found in any blacklist.
     */
    public List<Integer> getOccurrences() {
        return occurrences;
    }
}
