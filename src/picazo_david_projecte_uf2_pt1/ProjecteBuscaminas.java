package picazo_david_projecte_uf2_pt1;

import java.util.Scanner;

public class ProjecteBuscaminas {

    static Scanner teclat;

    /**
     * Mètode principal que inicia i executa el joc del Buscamines. Utilitza un
     * bucle principal per permetre al jugador tornar a jugar després de
     * completar una partida. Mostra un menú per seleccionar el nivell de
     * dificultat o permetre la personalització de les dades del joc.
     *
     * @param args els arguments passats al programa (no s'utilitzen)
     */
    public static void main(String[] args) {
        teclat = new Scanner(System.in);
        boolean tornarAjugar;

        do {
            int nivel = mostrarMenu();
            switch (nivel) {
                case 1:
                    jugar(8, 8, 19);
                    break;
                case 2:
                    jugar(16, 16, 40);
                    break;
                case 3:
                    jugar(16, 30, 99);
                    break;
                case 4:
                    int[] datos = demanarDades();
                    int fil = datos[0];
                    int col = datos[1];
                    int min = datos[2];
                    jugar(fil, col, min);
                    break;
            }

            String resposta = util.pedirString(teclat, "Vols seguir jugant?");
            tornarAjugar = resposta.equalsIgnoreCase("si") || resposta.equalsIgnoreCase("s");

        } while (tornarAjugar);
    }

    /**
     * Mostra un menú per seleccionar el nivell de dificultat o la
     * personalització de les dades del joc. Llegeix la opció introduïda per
     * l'usuari i la retorna.
     *
     * @return Un enter que representa el nivell de dificultat seleccionat: 1
     * per Principiant, 2 per Intermedi, 3 per Expert o 4 per Personalitzat.
     */
    public static int mostrarMenu() {
        int nivel;
        do {
            System.out.println("█████╗  ██╗   ██╗███████╗ ██████╗ █████╗ ███╗   ███╗██╗███╗   ██╗ █████╗ ███████╗");
            System.out.println("██╔══██╗██║   ██║██╔════╝██╔════╝██╔══██╗████╗ ████║██║████╗  ██║██╔══██╗██╔════╝");
            System.out.println("██████╦╝██║   ██║███████╗██║     ███████║██╔████╔██║██║██╔██╗ ██║███████║███████╗");
            System.out.println("██╔══██╗██║   ██║╚════██║██║     ██╔══██║██║╚██╔╝██║██║██║╚██╗██║██╔══██║╚════██║");
            System.out.println("██████╦╝╚██████╔╝███████║╚██████╗██║  ██║██║ ╚═╝ ██║██║██║ ╚████║██║  ██║███████║");
            System.out.println("╚═════╝  ╚═════╝ ╚══════╝ ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝");

            nivel = util.pedirEntero(teclat, "Selecciona el nivel:\n1. Principiante\n2. Intermedio\n3. Experto\n4. Personalizado\n");
            System.out.println();
            if (nivel != 1 && nivel != 2 && nivel != 3 && nivel != 4) {
                System.out.println("Debes seleccionar una opción válida");
            }
        } while (nivel != 1 && nivel != 2 && nivel != 3 && nivel != 4);

        return nivel;
    }

    /**
     * Demana a l'usuari que introdueixi el nombre de files, columnes i mines
     * per a la configuració personalitzada del joc Busca-mines. Llegeix les
     * dades introduïdes per l'usuari, verifica la seva validesa i les retorna
     * en un array d'enters.
     *
     * @return Un array d'enters amb les dades introduïdes per l'usuari: [0] -
     * Nombre de files. [1] - Nombre de columnes. [2] - Nombre de mines.
     */
    public static int[] demanarDades() {
        int fil, col, min;

        do {
            fil = util.pedirEntero(teclat, "Introduce el número de filas: ");
            col = util.pedirEntero(teclat, "Introduce el número de columnas: ");
            min = util.pedirEntero(teclat, "Introduce el número de minas: ");

            if (fil <= 0 || col <= 0 || min < 0 || min > fil * col) {
                System.out.println("Error: Comprueba que los números introducidos sean válidos");
            }
            teclat.nextLine();

        } while (fil <= 0 || col <= 0 || min < 0 || min > fil * col);

        int[] resultados = {fil, col, min};
        return resultados;
    }

    /**
     * Inicia i controla el desenvolupament del joc Buscamines amb la grandària
     * del tauler, la quantitat de mines i les regles específiques
     * proporcionades. Interactua amb l'usuari per obtenir entrades i gestionar
     * les accions en el tauler, mostrant el tauler actualitzat després de cada
     * moviment. Finalitza quan el joc està complet (totes les caselles segures
     * estan destapades) o quan el jugador selecciona una mina. Mostra el temps
     * transcorregut al finalitzar el joc.
     *
     * @param filas El número de files del tauler.
     * @param columnas El número de columnes del tauler.
     * @param cantidadMinas La quantitat de mines al tauler.
     */
    public static void jugar(int filas, int columnas, int cantidadMinas) {
        long tiempoInicio = System.currentTimeMillis();
        char[][] tablero = new char[filas][columnas];
        char[][] minas = new char[filas][columnas];
        int[][] solucion = new int[filas][columnas];

        iniciarJuego(filas, columnas, cantidadMinas, tablero, minas, solucion);

        boolean completado = false;

        do {
            mostrarTablero(tablero);

            int[] entrada = obtenerEntrada(filas, columnas, tablero);
            int a = entrada[0];
            int b = entrada[1];

            if (a == -2 && b == -2 || a == -2 || b == -2) {
                mostrarTableroConMinas(minas, tablero);
            } else {
                int x = a, y = b;
                if (tablero[x][y] == ' ') {

                    if (minas[x][y] == 'X') {
                        procesarDerrota(minas, tablero);
                        mostrarTiempoTranscurrido(tiempoInicio);

                        break;
                    } else {
                        destaparCasilla(tablero, solucion, x, y);
                        mostrarTiempoTranscurrido(tiempoInicio);
                    }

                    boolean victoria = verificarVictoria(tablero, cantidadMinas);

                    if (victoria) {
                        procesarVictoria(minas, tablero);
                        mostrarTiempoTranscurrido(tiempoInicio);

                        break;
                    } else {

                    }
                }

            }

        } while (!completado);
    }

    /**
     * Mostra el temps transcorregut des d'un moment específic en hores, minuts
     * i segons.
     *
     * @param tiempoInicio El temps en mil·lisegons en què es va iniciar el
     * recompte.
     */
    private static void mostrarTiempoTranscurrido(long tiempoInicio) {

        long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;

        long segundos = tiempoTranscurrido / 1000;
        long minutos = segundos / 60;
        long horas = minutos / 60;

        System.out.println("Tiempo de duración: " + horas + " horas, " + minutos % 60 + " minutos, " + segundos % 60 + " segundos");
    }

    /**
     * Obté les coordenades d'entrada de l'usuari per marcar una casella al
     * tauler. El mètode valida les coordenades introduïdes i s'assegura que
     * estiguin dins del rang i no s'hagin introduït prèviament. A més, permet
     * l'entrada especial de -2 per revelar totes les mines al tauler.
     *
     * @param filas El nombre de files al tauler.
     * @param columnas El nombre de columnes al tauler.
     * @param tablero El tauler actual del joc.
     * @return Un array de dos elements que representa les coordenades
     * introduïdes [fila, columna].
     */
    public static int[] obtenerEntrada(int filas, int columnas, char[][] tablero) {
        int[] entrada = new int[2];

        do {
            entrada[0] = util.pedirEntero(teclat, "Introduce la fila: ") - 1;
            entrada[1] = util.pedirEntero(teclat, "Introduce la columna: ") - 1;

            if (!(entrada[0] == -2 || entrada[1] == -2) && (entrada[0] < 0 || entrada[0] >= filas || entrada[1] < 0 || entrada[1] >= columnas)) {
                System.out.println("Error: Comprueba que los números introducidos no estén fuera de rango");
            } else if (!(entrada[0] < 0 || entrada[1] < 0) && (entrada[0] >= filas || entrada[1] >= columnas || tablero[entrada[0]][entrada[1]] != ' ')) {
                System.out.println("Ya has introducido anteriormente esta coordenada");
            }
        } while (!(entrada[0] == -2 || entrada[1] == -2) && (entrada[0] < 0 || entrada[0] >= filas || entrada[1] < 0 || entrada[1] >= columnas || tablero[entrada[0]][entrada[1]] != ' '));

        return entrada;
    }

    /**
     * Inicia un nou joc amb un tauler, mines i solució.
     *
     * @param filas El nombre de files al tauler.
     * @param columnas El nombre de columnes al tauler.
     * @param cantidadMinas La quantitat de mines al tauler.
     * @param tablero El tauler del joc.
     * @param minas La ubicació de les mines al tauler.
     * @param solucion La solució del joc amb la quantitat de mines adjacents.
     */
    private static void iniciarJuego(int filas, int columnas, int cantidadMinas, char[][] tablero, char[][] minas, int[][] solucion) {
        iniciarTablero(tablero, minas, filas, columnas, cantidadMinas);
        generarSolucion(minas, solucion);
    }

    /**
     * Processa l'estat del joc quan el jugador perd. Mostra el tauler revelant
     * totes les mines i la puntuació obtinguda.
     *
     * @param minas La ubicació de les mines al tauler.
     * @param tablero El tauler del joc.
     */
    private static void procesarDerrota(char[][] minas, char[][] tablero) {
        int sumaTablero = sumarTablero(tablero);

        mostrarTablero(tablero);
        mostrarTableroConMinas(minas, tablero);
        System.out.println("HAS PERDIDO !!!");
        System.out.println("Tu puntuación es de: " + sumaTablero);

    }

    /**
     * Destapa una casella al tauler revelant la solució.
     *
     * @param tablero El tauler del joc.
     * @param solucion La solució del joc amb la quantitat de mines adjacents.
     * @param x La coordenada x de la casella a destapar.
     * @param y La coordenada y de la casella a destapar.
     */
    private static void destaparCasilla(char[][] tablero, int[][] solucion, int x, int y) {
        tablero[x][y] = (solucion[x][y] + "").charAt(0);
    }

    /**
     * Processa l'estat del joc quan el jugador guanya. Mostra el tauler
     * revelant totes les mines i la puntuació obtinguda.
     *
     * @param minas La ubicació de les mines al tauler.
     * @param tablero El tauler del joc.
     */
    private static void procesarVictoria(char[][] minas, char[][] tablero) {
        int sumaTablero = sumarTablero(tablero);

        mostrarTablero(tablero);
        mostrarTableroConMinas(minas, tablero);
        System.out.println("HAS GANADO!!!");
        System.out.println("Tu puntuación es de: " + sumaTablero);
    }

    /**
     * Suma la puntuació del tauler considerant només les caselles amb dígits.
     *
     * @param tablero El tauler del joc.
     * @return La suma de la puntuació del tauler.
     */
    private static int sumarTablero(char[][] tablero) {
        int suma = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (Character.isDigit(tablero[i][j])) {
                    suma += Character.getNumericValue(tablero[i][j]);
                }
            }
        }
        return suma;
    }

    /**
     * Inicia el tauler amb les mines i les caselles buides.
     *
     * @param tablero El tauler del joc.
     * @param minas La ubicació de les mines al tauler.
     * @param filas El número de files del tauler.
     * @param columnas El número de columnes del tauler.
     * @param totalMinas La quantitat total de mines al tauler.
     */
    public static void iniciarTablero(char[][] tablero, char[][] minas, int filas, int columnas, int totalMinas) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                tablero[i][j] = ' ';
            }
        }
        int contadorMinas = 0;

        while (contadorMinas < totalMinas) {
            int x = (int) (Math.random() * filas);
            int y = (int) (Math.random() * columnas);
            if (minas[x][y] != 'X') {
                minas[x][y] = 'X';
                contadorMinas++;
            }
        }
    }

    /**
     * Genera la solució del tauler, calculant la quantitat de mines adjacents a
     * cada casella sense revelar. Si una casella conté una mina, s'estableix el
     * valor -1 a la solució. La solució es guarda en una matriu d'enters.
     *
     * @param minas Matriu de caràcters que representa el tauler de mines amb
     * 'X' indicant la presència de mines.
     * @param solucion Matriu d'enters que emmagatzemarà la solució del tauler
     * amb la quantitat de mines adjacents.
     */
    public static void generarSolucion(char[][] minas, int[][] solucion) {
        int filas = minas.length;
        int columnas = minas[0].length;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (minas[i][j] == 'X') {
                    solucion[i][j] = -1;
                }
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        int nuevaFila = i + k;
                        int nuevaColumna = j + l;

                        if (nuevaFila >= 0 && nuevaFila < filas && nuevaColumna >= 0 && nuevaColumna < columnas) {
                            if (minas[nuevaFila][nuevaColumna] == 'X') {
                                solucion[i][j]++;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Mostra el tauler de joc amb les seves cel·les.
     *
     * @param tablero El tauler actual del joc.
     */
    public static void mostrarTablero(char[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            // Línia superior de la cel·la
            for (int j = 0; j < tablero[0].length; j++) {
                System.out.print("+---");
            }
            System.out.println("+");

            // contingut de la cel·la
            for (int j = 0; j < tablero[0].length; j++) {
                System.out.printf("| %s ", (tablero[i][j] == '\u0000') ? " " : tablero[i][j]); // Espacio adicional después del número
            }
            System.out.println("|");
        }

        // Línea inferior del taulell
        for (int j = 0; j < tablero[0].length; j++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }

    /**
     * Mostra el tauler amb les mines revelades juntament amb el tauler actual.
     *
     * @param minas La ubicació de les mines al tauler.
     * @param tablero El tauler actual del joc.
     */
    private static void mostrarTableroConMinas(char[][] minas, char[][] tablero) {
        System.out.println("Tablero Desbloqueado:");
        mostrarTablero(tablero);
        System.out.println("Posición de las Minas:");
        mostrarTablero(minas);
    }

    /**
     * Verifica si se ha alcanzado la victoria en el juego. Comprueba si el
     * número de casillas seguras restantes en el tablero es igual a la cantidad
     * total de minas.
     *
     * @param tablero El tablero actual del juego.
     * @param cantidadMinas La cantidad total de minas en el tablero.
     * @return `true` si se ha alcanzado la victoria, `false` en caso contrario.
     */
    public static boolean verificarVictoria(char[][] tablero, int cantidadMinas) {
        int posiblesMinas = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] == ' ') {
                    posiblesMinas++;
                }
            }
        }
        return posiblesMinas == cantidadMinas;
    }
}
