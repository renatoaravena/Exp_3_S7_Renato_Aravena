import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;


public class TeatroMoro {

    //VARIABLES

    //Variables constantes del teatro (el final significa que no se pueden modificar)
    private static final String NOMBRE_TEATRO = "Teatro Moro";
    private static final int CAPACIDAD_TOTAL = 100;
    private static final int PRECIO_VIP = 30000;
    private static final int PRECIO_PLATEA_ALTA = 18000;
    private static final int PRECIO_PLATEA_BAJA = 15000;
    private static final int PRECIO_PALCOS = 13000;

    //Variables de control
    private static int entradasDisponibles = CAPACIDAD_TOTAL;
    private static int entradasVendidas = 0;
    private static int ingresosTotales = 0;

    //Lista para almacenar las entradas vendidas con sus datos(dentro de esta lista debe haber listas con los datos de las entradas)
    //Ejemplo: entradasVendidasList.get(0) = [nombre, rut, cantidadEntradas, tipoEntrada, precioTotal]
    //Ejemplo: entradasVendidasList.get(0).get(0) = nombre

    private static final List<List<Object>> entradasVendidasList = new ArrayList<>();




    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        boolean salir = false; //Cuando no quiera comprar más entradas

        //Menu principal
        while (!salir) {

            System.out.println("\n=== " + NOMBRE_TEATRO + " ===");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Entradas disponibles: " + entradasDisponibles);
            TimeUnit.SECONDS.sleep(1);

            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Comprar entradas");
            System.out.println("2. Imprimir ultima boleta");
            System.out.println("3. Resumen de ventas");
            System.out.println("4. Salir");

            TimeUnit.SECONDS.sleep(1);
            System.out.print("\nSeleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    TimeUnit.SECONDS.sleep(1);
                    comprarEntradas(scanner);
                    break;
                case "2":
                    TimeUnit.SECONDS.sleep(1);
                    imprimirUltimaBoleta();
                    break;
                case "3":
                    TimeUnit.SECONDS.sleep(1);
                    resumenVentas();
                    break;
                case "4":
                    salir = true;
                    System.out.println("\nGracias por usar el sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        scanner.close();
    } //MENU PRINCIPAL


    //FUNCION PARA COMPRAR ENTRADAS
    private static void comprarEntradas(Scanner scanner) throws InterruptedException {


        //Lista de la compra, la cual estará de tal forma
        //Ejemplo: compra.get(0) = [ZONA,CANT ENTRADAS ,COSTO BASE, DESCUENTO APLICADO, COSTO FINAL]
        List<Object> compra = new ArrayList<>();

        System.out.println("\n=== COMPRAR ENTRADAS ===");

        if (entradasDisponibles <= 0) {
            System.out.println("No hay entradas disponibles para comprar.");
            return;
        }

        TimeUnit.SECONDS.sleep(1);

        System.out.println("\nTipos de entradas disponibles:");
        System.out.println("1. VIP ($" + PRECIO_VIP + ")");
        System.out.println("2. Platea Alta ($" + PRECIO_PLATEA_ALTA + ")");
        System.out.println("3. Platea Baja ($" + PRECIO_PLATEA_BAJA + ")");
        System.out.println("4. Palcos ($" + PRECIO_PALCOS + ")");

        // Seleccionar tipo de entrada
        boolean zonaValida = false;

        while (!zonaValida) {
            System.out.print("\nSeleccione el tipo de entrada (1-4): ");
            String tipo = scanner.nextLine();

            switch (tipo) {
                case "1":
                    compra.add("VIP");
                    compra.add(PRECIO_VIP);
                    zonaValida = true;
                    break;
                case "2":
                    compra.add("Platea Alta");
                    compra.add(PRECIO_PLATEA_ALTA);
                    zonaValida = true;
                    break;
                case "3":
                    compra.add("Platea Baja");
                    compra.add(PRECIO_PLATEA_BAJA);
                    zonaValida = true;
                    break;
                case "4":
                    compra.add("Palcos");
                    compra.add(PRECIO_PALCOS);
                    zonaValida = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }


        // Validación de edad y descuentos
        int edad;
        boolean edadValida = false;
        double descuento = 0;

        while (!edadValida) {

            try {
                System.out.print("\nIngrese su edad: ");
                edad = Integer.parseInt(scanner.nextLine());

                if (edad <= 16 || edad >= 116) {
                    System.out.println("¡Edad inválida!");
                } else {
                    edadValida = true;
                    if (edad >= 60) {
                        descuento = 0.15;
                        compra.add(descuento);
                    } else {
                        // Preguntar si es estudiante
                        String esEstudiante;

                        do{
                            System.out.print("¿Es estudiante? (Si/No): ");
                            esEstudiante = scanner.nextLine();
                            if (esEstudiante.equalsIgnoreCase("Si")) {
                                descuento = 0.10;
                                compra.add(descuento);
                                break;
                            } else if (esEstudiante.equalsIgnoreCase("No")) {
                                descuento = 0;
                                compra.add(descuento);
                                break;
                            } else {
                                System.out.println("Opción inválida. Intente nuevamente.");
                            }

                        }while (!esEstudiante.equalsIgnoreCase("Si") && !esEstudiante.equalsIgnoreCase("No"));
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("¡Error! Debe ingresar un número.");
            }
        }


        // Calcular total
        int precioFinal = (int) ((int) compra.get(1) * (1 - (double)compra.get(2)));
        compra.add(precioFinal);

        TimeUnit.SECONDS.sleep(1);
        // Confirmar compra
        System.out.println("\nResumen de compra:");
        System.out.println("Tipo: " + compra.get(0));
        if (descuento > 0) {
            System.out.println("Descuento aplicado: " + String.format("%.0f", (double)compra.get(2) * 100) + "%");
        }
        System.out.println("Total a pagar: $" + (int)compra.get(3));

        String confirmacion;
        TimeUnit.SECONDS.sleep(1);

        do {

            System.out.print("\n¿Confirmar compra? (Si/No): ");

            confirmacion = scanner.nextLine();

            if (confirmacion.equalsIgnoreCase("Si")) {

                // Actualizar disponibilidad
                entradasDisponibles -= 1;
                entradasVendidas += 1;
                ingresosTotales += precioFinal;

                // Registrar última transacción
                System.out.println("\n=== COMPRA EXITOSA ===");
                System.out.println("Imprimiendo boleta...");
                TimeUnit.SECONDS.sleep(3);

                System.out.println("-------------------------------");
                System.out.println("          TEATRO MORO          ");
                System.out.println("-------------------------------");
                System.out.println("Ubicación: " + compra.get(0));
                System.out.println("Costo Base : $" + compra.get(1));
                System.out.println("Descuento aplicado: " + String.format("%.0f", (double)compra.get(2) * 100) + "%");
                System.out.println("Costo Final: $" + (int) compra.get(3));
                System.out.println("-------------------------------");
                System.out.println("Gracias por su visita al Teatro Moro");
                System.out.println("-------------------------------");

                entradasVendidasList.add(compra);
                TimeUnit.SECONDS.sleep(1);
                break;
            } else if (confirmacion.equalsIgnoreCase("No")) {
                System.out.println("Compra cancelada.");
                break;
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }while (!confirmacion.equalsIgnoreCase("Si") && !confirmacion.equalsIgnoreCase("No"));
    }

    //FUNCION PARA IMPRIMIR LA ULTIMA BOLETA
    @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
    private static void imprimirUltimaBoleta() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);

        if (entradasVendidasList.isEmpty()) {// Verifica si hay boletas para imprimir
            System.out.println("No hay boletas para imprimir.");
            return;
        }

        List<Object> ultimaBoleta = entradasVendidasList.get(entradasVendidasList.size() - 1); //Obtenemos la ultima boleta de la lista de entradas vendidas
        //Creamos una lista de otra forma deberíamos estar llamando a la lista de entradasVendidasList.get(entradasVendidasList.size() - 1).get(0) y eso es muy largo

        System.out.println("\n==== ULTIMA BOLETA ====");
        System.out.println("-------------------------------");
        System.out.println("          TEATRO MORO          ");
        System.out.println("-------------------------------");
        System.out.println("Ubicación: " + ultimaBoleta.get(0));
        System.out.println("Costo Base : $" + ultimaBoleta.get(1));
        System.out.println("Descuento aplicado: " + String.format("%.0f", (double)ultimaBoleta.get(2) * 100) + "%");
        System.out.println("Costo Final: $" + ultimaBoleta.get(3));
        System.out.println("-------------------------------");
        System.out.println("Gracias por su visita al Teatro Moro");
        System.out.println("-------------------------------");

    }

    //FUNCION PARA RESUMEN DE VENTAS
    private static void resumenVentas() throws InterruptedException { //debe mostrar ubicacion, precio inicial, descuento y precio final de forma ordenada

        TimeUnit.SECONDS.sleep(1);
        System.out.println("\n===== RESUMEN DE VENTAS =====");
        System.out.println("-------------------------------");
        System.out.println("          TEATRO MORO          ");
        System.out.println("-------------------------------");
        System.out.println("Entradas vendidas: " + entradasVendidas);
        System.out.println("Entradas disponibles: " + entradasDisponibles);
        System.out.println("Ingresos totales: $" + ingresosTotales);
        System.out.println("-------------------------------");

        for (List<Object> entrada : entradasVendidasList) {
            System.out.println("Entrada #" + (entradasVendidasList.indexOf(entrada) + 1));
            System.out.println("Ubicación: " + entrada.get(0));
            System.out.println("Costo Base : $" + entrada.get(1));
            System.out.println("Descuento aplicado: " + String.format("%.0f", (double)entrada.get(2) * 100) + "%");
            System.out.println("Costo Final: $" + entrada.get(3));
            System.out.println("-------------------------------");
        }

    }

}
