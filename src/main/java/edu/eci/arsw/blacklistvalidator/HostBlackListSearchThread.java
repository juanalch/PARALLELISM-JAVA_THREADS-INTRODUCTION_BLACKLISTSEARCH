package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
    private int checkedLists = 0;
    // new local occurrences list
    private final List<Integer> occurrences = new LinkedList<>();

    // new shared state
    private final AtomicInteger globalOccurrences;
    private final AtomicBoolean stopSearch;


    /**
     * Creates a new blacklist search thread.
     *
     * @param startIndex    starting index (inclusive) of the range to search
     * @param endIndex      ending index (exclusive) of the range to search
     * @param ipaddress     IP address to validate (e.g., "200.24.34.55")
     * @param facade        instance of the blacklist data source facade
     */

    public HostBlackListSearchThread(int startIndex,int endIndex,String ipaddress,HostBlacklistsDataSourceFacade facade, AtomicInteger globalOccurrences, AtomicBoolean stopSearch) {

        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.ipaddress = ipaddress;
        this.facade = facade;
        this.globalOccurrences = globalOccurrences;
        this.stopSearch = stopSearch;
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

        for (int i = startIndex; i < endIndex && !stopSearch.get(); i++) {

            checkedLists++;

            if (facade.isInBlackListServer(i, ipaddress)) {
                occurrences.add(i);

                // atomic incremet of the global count of occurrences
                int total = globalOccurrences.incrementAndGet();

                // If the limit is reached, stop the global search
                if (total >= HostBlackListsValidator.BLACK_LIST_ALARM_COUNT) {
                    stopSearch.set(true);
                    break;
                }
            }
        }
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

    /**
     * Returns the total number of blacklists this thread reviewed.
     *
     * @return number of blacklists queried in the assigned range
     */
    public int getCheckedLists() {
        return checkedLists;
    }
}

