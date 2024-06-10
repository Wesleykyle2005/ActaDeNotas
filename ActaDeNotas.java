import java.util.InputMismatchException;
import java.util.Scanner;

public class ActaDeNotas {

    public static void main(String[] args) {
          boolean seguirUsando = true;
          Scanner scanner = new Scanner(System.in);
          while(seguirUsando){
        try{
            
            
            System.out.print("Ingrese la cantidad de estudiantes: ");
            int cantidadEstudiantes = (int) solicitarInt(scanner, "Cantidad de estudiantes", 1, 33);
            scanner.nextLine();
            // Datos generales inicializados del curso
            Object[][] datosGenerales = new Object[][] {
                    { "Nombre del curso", "INTRODUCCION A LA PROGRAMACIÓN" },
                    { "Período Lectivo", "PRIMER SEMESTRE 2021" },
                    { "Carrera", "INGENIERIA EN SISTEMAS" },
                    { "Modalidad", "Regular" },
                    { "Cod. curso", 30849 },
                    { "Grupo", "1M1-IS" },
                    { "Cod. Asignatura", "S1004" },
                    { "Cod. Programa", 1004 },
                    { "Cantidad de estudiantes", cantidadEstudiantes }
            };

            // Imprimir los datos generales del curso en formato de tabla
            System.out.println("Datos generales:");
            System.out.println(
                    "+--------------------+------------------------------+---------------+----------+");
            for (int i = 0; i < 4; i++) {
                System.out.printf("|%-20s|%-30s|%-15s|%-10s|\n", datosGenerales[i][0], datosGenerales[i][1],
                        datosGenerales[i + 4][0], datosGenerales[i + 4][1]);
                System.out.println(
                        "+--------------------+------------------------------+---------------+----------+");
            }

            // Crear un arreglo de estudiantes del tamaño de la cantidad de estudiantes e
            // inicializar los encabezados
            Object[][] datosEstudiantes = new Object[(int) datosGenerales[datosGenerales.length - 1][1] + 1][12];

            // Encabezados de los datos de los estudiantes
            datosEstudiantes[0] = new Object[] {
                    "Carnet", "Apellidos y Nombres", "IP", "IIP", "SIST",
                    "PROY", "N.F.", "EXA. I CONV", "N.F. I CONV",
                    "II CONV", "Aprob?", "Deserción?"
            };

            // Pedir los datos de los estudiantes
            for (int i = 1; i <= cantidadEstudiantes; i++) {

                int nf = 0;
                int iip = 0;
                int proy = 0;
                int exaiconv = 0;
                int nficonv = 0;
                int exaiiconv = 0;
                String aprobado = "No";
                String desercion;
                System.out.println("Ingrese los datos del estudiante " + i + ":");
                String carnet = solicitarString(scanner, "Carnet", 5, 50);
                String nombre = solicitarString(scanner, "Apellidos y Nombres", 5, 50);
                int ip = (int) solicitarInt(scanner, "Primer parcial (IP): ", 0, 35);
                if (!proyecto(scanner)) {
                    iip = (int) solicitarInt(scanner, "Segundo parcial (IIP): ", 0, 35);
                } else {
                    proy = (int) solicitarInt(scanner, "Proyecto de curso (PROY): ", 0, 35);
                }
                int sist = (int) solicitarInt(scanner, "Sistemáticos (SIST): ", 0, 30);
                nf = ip + iip + sist + proy;
                if (nf < 60) {
                    exaiconv = (int) solicitarInt(scanner, "Examen primera convocatoria (EXA. I CONV): ", 0, 70);
                    nficonv = sist + exaiconv;
                    if (nficonv < 60) {
                        exaiiconv = (int) solicitarInt(scanner, "Examen segunda convocatoria (EXA. II CONV): ", 0,
                                100);
                    }
                }

                if (nf >= 60 || exaiiconv >= 60 || nficonv >= 60) {
                    aprobado = "SI";
                }

                do {
                    System.out.print("¿Deserción? (Si/No): ");
                    desercion = scanner.nextLine().toUpperCase();
                } while (!desercion.equals("SI") && !desercion.equals("NO"));

                datosEstudiantes[i] = new Object[] { carnet, nombre, ip, iip, sist, proy, nf, exaiconv, nficonv,
                        exaiiconv, aprobado, desercion };
            }

            ordenarEstudiantesPorNotasAscendente(datosEstudiantes, cantidadEstudiantes);
            Object[][] reporteFinal = new Object[2][10];

            reporteFinal[0] = new Object[] {
                    "Matricula Ini.", "Matricula efect.", "Num. de deserciones", "Cant. de aprob",
                    "% de aprob.", "Cantidad de reprob", "% de reprob", "Nota Min[N.F.]", "Nota Max[N.F.]",
                    "Prom. notas[N.F.]"
            };
            //Revisado
            int matricula = cantidadEstudiantes;
            int matriculaEfectiva = calcularMatriculaEfectiva(datosEstudiantes, cantidadEstudiantes);
            int numeroDeserciones = cantidadEstudiantes - matriculaEfectiva;
            //
            
            int cantidadAprobados = calcularCantidadAprobados(datosEstudiantes, cantidadEstudiantes);
            //
            double porcentajeAprobados = (double) cantidadAprobados / cantidadEstudiantes * 100;
            
            int cantidadReprobados = cantidadEstudiantes - cantidadAprobados;
            double porcentajeReprobados = 100 - porcentajeAprobados;
            //
            int notaMinima = (int) datosEstudiantes[1][6];
            int notaMaxima = (int) datosEstudiantes[cantidadEstudiantes][6];
            
            double promedioDeNotas = calcularPromedioNotas(datosEstudiantes, cantidadEstudiantes);

            reporteFinal[1] = new Object[] { matricula, matriculaEfectiva, numeroDeserciones, cantidadAprobados,
                    porcentajeAprobados, cantidadReprobados, porcentajeReprobados, notaMinima, notaMaxima,
                    promedioDeNotas };
            ordenarEstudiantesAlfabeticamente(datosEstudiantes, cantidadEstudiantes);
            System.out.println("Reporte");
            System.out.println(
                    "+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");
                    
            for (int i = 0; i < reporteFinal.length; i++) {
                System.out.printf(
                        "|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|\n",
                        reporteFinal[i][0], reporteFinal[i][1], reporteFinal[i][2], reporteFinal[i][3],
                        reporteFinal[i][4], reporteFinal[i][5], reporteFinal[i][6], reporteFinal[i][7],
                        reporteFinal[i][8], reporteFinal[i][9]);
                System.out.println(
                        "+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");
            }
            
            for (int i = 0; i < datosEstudiantes.length; i++) {
                System.out.printf(
                        "|%-10s|%-30s|%-5s|%-5s|%-5s|%-5s|%-5s|%-15s|%-12s|%-10s|%-7s|%-10s|\n",
                        datosEstudiantes[i][0], datosEstudiantes[i][1], datosEstudiantes[i][2], datosEstudiantes[i][3],
                        datosEstudiantes[i][4], datosEstudiantes[i][5], datosEstudiantes[i][6], datosEstudiantes[i][7],
                        datosEstudiantes[i][8], datosEstudiantes[i][9], datosEstudiantes[i][10],
                        datosEstudiantes[i][11]);
                System.out.println(
                        "+----------+------------------------------+-----+-----+-----+-----+-----+---------------+------------+----------+-------+----------+");

            }
            
           seguirUsando= usarPreguntar(scanner);
            
          
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
      }
      scanner.close();
    }
    private static double calcularPromedioNotas(Object[][] datosEstudiantes, int cantidadEstudiantes) {
        double promedioDeNotas = 0;
        for (int i = 1; i <= cantidadEstudiantes; i++) {
            promedioDeNotas += (int) datosEstudiantes[i][6];
        }
        return promedioDeNotas / cantidadEstudiantes;
    }

    private static int calcularCantidadAprobados(Object[][] datosEstudiantes, int cantidadEstudiantes) {
        int cantidadAprobados = 0;
        for (int i = 1; i <= cantidadEstudiantes; i++) {
            int nf = (int) datosEstudiantes[i][6]; // N.F.
            int nfIConv = (int) datosEstudiantes[i][8]; // N.F. I CONV
            int iiConv = (int) datosEstudiantes[i][9]; // II CONV
            if (nf >= 60 || nfIConv >= 60 || iiConv >= 60) {
                cantidadAprobados++;
            }
        }
        return cantidadAprobados;
    }


    private static int calcularMatriculaEfectiva(Object[][] datosEstudiantes, int cantidadEstudiantes) {
        int matriculaEfectiva = cantidadEstudiantes;
        for (int i = 1; i <= cantidadEstudiantes; i++) {
            if (((String) datosEstudiantes[i][11]).equalsIgnoreCase("SI")) {
                matriculaEfectiva--;
            }
        }
        return matriculaEfectiva;
    }

    private static boolean proyecto(Scanner scanner) {
        Boolean exists;

        int opcion = (int) solicitarInt(scanner, "Tiene proyecto de curso?\n 1. SI/si\n 2. NO/no", 1, 2);

        if (opcion == 1) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }

    private static int solicitarInt(Scanner scanner, String mensaje, int min, int max) {
        int valor = 0;
        while (true) {
            try {
                System.out.println(mensaje);
                valor = scanner.nextInt();
                scanner.nextLine();
                if (valor < min || valor > max) {
                    System.out.println("Debe ser un número entre " + min + " y " + max);
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
        return valor;
    }

    private static String solicitarString(Scanner scanner, String mensaje, int minLength, int maxLength) {
        String valor = "";
        while (true) {
            System.out.print(mensaje + ": ");
            valor = scanner.nextLine();
            if (valor.length() < minLength || valor.length() > maxLength) {
                System.out.println("La longitud debe estar entre " + minLength + " y " + maxLength + " caracteres.");
                continue;
            }
            break;
        }
        return valor;
    }

    private static void ordenarEstudiantesAlfabeticamente(Object[][] datosEstudiantes, int cantidadEstudiantes) {
        for (int i = 1; i <= cantidadEstudiantes - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j <= cantidadEstudiantes; j++) {
                if (((String) datosEstudiantes[j][1]).compareTo((String) datosEstudiantes[minIndex][1]) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Object[] temp = datosEstudiantes[i];
                datosEstudiantes[i] = datosEstudiantes[minIndex];
                datosEstudiantes[minIndex] = temp;
            }
        }
    }

    private static void ordenarEstudiantesPorNotasAscendente(Object[][] datosEstudiantes, int cantidadEstudiantes) {
        for (int i = 1; i <= cantidadEstudiantes - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j <= cantidadEstudiantes; j++) {
                int notaActual = (int) datosEstudiantes[j][6]; // Índice 6 es la columna de las notas
                int notaMinima = (int) datosEstudiantes[minIndex][6];
                if (notaActual < notaMinima) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Object[] temp = datosEstudiantes[i];
                datosEstudiantes[i] = datosEstudiantes[minIndex];
                datosEstudiantes[minIndex] = temp;
            }
        }
    }

    private static boolean usarPreguntar(Scanner scanner){
      int respuesta= solicitarInt(scanner, "Seleccione: \n 1. Usar de nuevo. \n 2. Terminar el programa", 1, 2);
      return respuesta !=2;
      }
    }