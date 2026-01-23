package edu.eci.arsw.blacklistvalidator;
import edu.eci.arsw.spamkeywordsdatasource.*;
import java.util.*;


public class HostBlackListSearchThread extends Thread{

    private final int startIndex;
    private final int endIndex;
    private final String ipaddress;
    private final HostBlacklistsDataSourceFacade facade;
    private final List<Integer> occurrences = new LinkedList<>();
    private int checkedLists = 0;

    public HostBlackListSearchThread(int startIndex, int endIndex, String ipaddress, HostBlacklistsDataSourceFacade facade){
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.ipaddress = ipaddress;
        this.facade = facade;
        
    }

    @Override
    public void run () {
        for (int i = startIndex; i < endIndex; i++){
            checkedLists++;
            if (facade.isInBlackListServer(i, ipaddress)){
                occurrences.add(i);
            }
        }
    }
    
    public int getCheckedLists(){
        return checkedLists;
    
    }

    public List<Integer> getOccurrences() {
        return occurrences;
    }
    
}
