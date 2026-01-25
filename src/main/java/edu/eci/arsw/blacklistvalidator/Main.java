/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;

/**
 *
 * @author hcadavid
 */
public class Main {
    
    public static void main(String a[]){

        //Parte 2: Uso de hilos

        HostBlackListsValidator hblv=new HostBlackListsValidator();
        System.out.println("Checking host: 200.24.34.55");
        List<Integer> blackListOcurrences=hblv.checkHost("200.24.34.55", 10);
        System.out.println("The host was found in the following blacklists: " +  blackListOcurrences);

        HostBlackListsValidator hblv2=new HostBlackListsValidator();
        System.out.println("Checking host: 212.24.24.55");
        List<Integer> blackListOcurrences2=hblv2.checkHost("212.24.24.55", 10);
        System.out.println("The host was found in the following blacklists: " + blackListOcurrences2);
        
        HostBlackListsValidator hblv3=new HostBlackListsValidator();
        System.out.println("Checking host: 202.24.34.55");
        List<Integer> blackListOcurrences3 = hblv3.checkHost("202.24.34.55", 10);
        System.out.println("The host was found in the following blacklists: " + blackListOcurrences3);

        
        
    }
    
}
