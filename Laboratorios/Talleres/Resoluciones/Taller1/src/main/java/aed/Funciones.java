package aed;

class Funciones {
    int cuadrado(int x) {
            return x*x;
    }

    double distancia(double x, double y) {
        return Math.sqrt((x*x) + (y*y));
    }

    boolean esPar(int n) {
        if (n % 2 == 0) {
            return true;
        } else {
                return false;
            }
    }

    boolean esBisiesto(int n) {
        if (n % 400 == 0 || n % 100 != 0 && n % 4 == 0){
            return true;
        } else {
            return false;
        }
    }

    int factorialIterativo(int n) {
        int res = 1;
        while (n != 0){
            res = res * n;
            n = n-1;
        } return res;    
    }

    int factorialRecursivo(int n) {
        if (n == 0 || n == 1){
            return 1;
        } else {
            return (factorialRecursivo(n-1) * n);
        }
    }

    boolean esPrimo(int n) {
        boolean res = true;
        if (n < 2){
            return false;
        } for (int i = 1; i <= n; i += 1){
            if ((i != n && i != 1) && (n % i == 0)){
                return false;
            }
        } return res;
        }

    int sumatoria(int[] numeros) {
        int res = 0;
        for (int n:numeros){
            res = res + n;
        } return res;
    }

    int busqueda(int[] numeros, int buscado) {
        int res = 0;
        for (int i = 0; i < numeros.length; i += 1){
            if (numeros[i] == buscado){
                res = i;
            }
        } return res;
    }

    boolean tienePrimo(int[] numeros) {
        boolean res = false;
        for (int i = 0; i < numeros.length; i += 1){
            if (esPrimo(numeros[i])){
                return true;
            }
        } return res;
    }

    boolean todosPares(int[] numeros) {
        boolean res = true;
        for (int n:numeros){
            if (n % 2 != 0){
                return false;
            }
        } return res;
    }

    boolean esPrefijo(String s1, String s2) {
        boolean res = true;
        if (s1.length() > s2.length()){
            return false;
        } else {
            for (int i = 0; i < s1.length(); i += 1){
                if (s1.charAt(i) != (s2.charAt(i))){
                return false;
                }
            }
        }
        return res;
    }

    boolean esSufijo(String s1, String s2) { // lo mismo que el anterior pero empezando desde el sX.length para atrás
        boolean res = true;
        if (s1.length() > s2.length()){
            return false;
        } else {
            for (int i = 0; i < s1.length(); i += 1){
                if (s1.charAt((s1.length()-i-1)) != (s2.charAt((s2.length()-i-1)))){
                return false;
                }
            }
        }
        return res;
    }
}
