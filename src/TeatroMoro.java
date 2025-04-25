import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;
//System.out.println("Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));



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
    private static double ingresosTotales = 0;

    //Lista para almacenar las entradas vendidas con sus datos(dentro de esta lista habrán listas con los datos de las entradas)
    //Ejemplo: entradasVendidasList.get(0) = [nombre, rut, cantidadEntradas, tipoEntrada, precioTotal]
    //Ejmeplo: entradasVendidasList.get(0).get(0) = nombre

    private static List<List<Object>> entradasVendidasList = new ArrayList<>(); // es static porque es de la clase por eso esta fuera del psvm// es static porque es de la clase por eso esta fuera del psvm

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        boolean salir = false; //Cuando no quiuera comprar mas entradas

        //Menu principal

        while (!salir) {
            System.out.println("\n=== " + NOMBRE_TEATRO + " ===");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Entradas disponibles: " + entradasDisponibles);
            TimeUnit.SECONDS.sleep(2);

            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Comprar entradas");
            System.out.println("2. Reservar entradas");
            System.out.println("3. Convertir reserva a compra");
            System.out.println("4. Modificar última venta");
            System.out.println("5. Imprimir última boleta");
            System.out.println("6. Mostrar estadísticas");
            System.out.println("7. Salir");

            TimeUnit.SECONDS.sleep(1);

            System.out.print("\nSeleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    comprarEntradas(scanner);
                    break;
                case "2":
                    //imprimirUltimaBoleta();
                    break;
                case "3":
                    //resumenVentas();
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


    private static void comprarEntradas(Scanner scanner) {


        //Lista de la compra, la cual estara de tal forma
        //Ejemplo: compra.get(0) = [ZONA,CANT ENTRADAS ,COSTO BASE, DESCUENTO APLICADO, COSTO FINAL]
        List<Object> compra = new ArrayList<>();

        System.out.println("\n=== COMPRAR ENTRADAS ===");

        if (entradasDisponibles <= 0) {
            System.out.println("No hay entradas disponibles para comprar.");
            return;
        }

        System.out.println("\nTipos de entradas disponibles:");
        System.out.println("1. VIP ($" + PRECIO_VIP + ")");
        System.out.println("2. Platea Alta ($" + PRECIO_PLATEA_ALTA + ")");
        System.out.println("3. Platea Baja ($" + PRECIO_PLATEA_BAJA + ")");
        System.out.println("4. Palcos ($" + PRECIO_PALCOS + ")");

        // Seleccionar tipo de entrada
        boolean zonaValida = false;
        String zona = "";
        int precioBase = 0;

        while (!zonaValida) {
            System.out.print("\nSeleccione el tipo de entrada (1-4): ");
            String tipo = scanner.nextLine();

            switch (tipo) {
                case "1":
                    zona = "VIP";
                    precioBase = PRECIO_VIP;
                    compra.add(zona);
                    compra.add(precioBase);
                    zonaValida = true;
                    break;
                case "2":
                    zona = "PLATEA ALTA";
                    precioBase = PRECIO_PLATEA_ALTA;
                    compra.add(zona);
                    compra.add(precioBase);
                    zonaValida = true;
                    break;
                case "3":
                    zona = "PLATEA BAJA";
                    precioBase = PRECIO_PLATEA_BAJA;
                    compra.add(zona);
                    compra.add(precioBase);
                    zonaValida = true;
                    break;
                case "4":
                    zona = "PALCOS";
                    precioBase = PRECIO_PALCOS;
                    compra.add(zona);
                    compra.add(precioBase);
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
        String tipoCliente = "Normal";

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
                        tipoCliente = "Tercera Edad";
                    } else {
                        // Preguntar si es estudiante
                        String esEstudiante;

                        do{
                            System.out.print("¿Es estudiante? (Si/No): ");
                            esEstudiante = scanner.nextLine();
                            if (esEstudiante.equalsIgnoreCase("Si")) {
                                descuento = 0.10;
                                compra.add(descuento);
                                tipoCliente = "Estudiante";
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
        double precioFinal = precioBase * (1 - descuento);
        compra.add(precioFinal);

        // Confirmar compra
        System.out.println("\nResumen de compra:");
        System.out.println("Tipo: " + zona);
        if (descuento > 0) {
            System.out.println("Descuento aplicado: " + (descuento * 100) + "%");
        }
        System.out.println("Total a pagar: $" + String.format("%.0f", precioFinal));


    }




}
