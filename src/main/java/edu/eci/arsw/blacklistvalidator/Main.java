package edu.eci.arsw.blacklistvalidator;

import java.util.List;

/**
 *
 * @author hcadavid
 */
import java.util.List;

/**
 * Clase Main: Punto de entrada del programa.
 * Actualmente, la lógica de validación se encuentra comentada para evitar su ejecución,
 * pero se mantiene como documentación de la Parte 2: Uso de hilos.
 */
public class Main {

    public static void main(String[] args) {

        /* * ===========================================================================
         * PARTE 2: USO DE HILOS
         * ---------------------------------------------------------------------------
         * Los siguientes bloques de código ilustran cómo validar diferentes direcciones 
         * IP (hosts) consultando múltiples listas negras de forma paralela 
         * utilizando la clase HostBlackListsValidator.
         * ===========================================================================
         */

        /*
        // --- Validación del Host 1 ---
        // Se crea el validador y se busca la IP 200.24.34.55 usando 10 hilos.
        HostBlackListsValidator hblv = new HostBlackListsValidator();
        System.out.println("Checking host: 200.24.34.55");
        List<Integer> blackListOcurrences = hblv.checkHost("200.24.34.55", 10);
        System.out.println("The host was found in the following blacklists: " + blackListOcurrences);

        // --- Validación del Host 2 ---
        // Se realiza la búsqueda para una segunda IP diferente.
        HostBlackListsValidator hblv2 = new HostBlackListsValidator();
        System.out.println("Checking host: 212.24.24.55");
        List<Integer> blackListOcurrences2 = hblv2.checkHost("212.24.24.55", 10);
        System.out.println("The host was found in the following blacklists: " + blackListOcurrences2);
        
        // --- Validación del Host 3 ---
        // Se realiza la búsqueda para una tercera IP de prueba.
        HostBlackListsValidator hblv3 = new HostBlackListsValidator();
        System.out.println("Checking host: 202.24.34.55");
        List<Integer> blackListOcurrences3 = hblv3.checkHost("202.24.34.55", 10);
        System.out.println("The host was found in the following blacklists: " + blackListOcurrences3);
        */
        

        String ip = "202.24.34.55";
        HostBlackListsValidator validator = new HostBlackListsValidator();

        int cores = Runtime.getRuntime().availableProcessors();

        int[] threadConfigs = {
            1,
            cores,
            cores * 2,
            50,
            100
        };

        for (int n : threadConfigs) {

            System.out.println("\n==============================");
            System.out.println("Threads: " + n);
            System.out.println("==============================");

            long startTime = System.currentTimeMillis();

            List<Integer> result = validator.checkHost(ip, n);

            long endTime = System.currentTimeMillis();

            System.out.println("Occurrences found: " + result.size());
            System.out.println("Execution time: " + (endTime - startTime) + " ms");

            // Pausa para observar en jVisualVM
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

