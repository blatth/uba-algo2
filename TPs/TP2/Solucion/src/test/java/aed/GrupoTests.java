package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class GrupoTests {

    @Test
    public void testHackearUnicaTransaccion() { // se erifica que hackearTx elimina correctamente la única transacción y se revierten los saldos
        Berretacoin bc = new Berretacoin(2);
        Transaccion[] trans = {
            new Transaccion(0, 1, 2, 50)
        };
        bc.agregarBloque(trans);
        bc.hackearTx();

        assertEquals(0, bc.txUltimoBloque().length); // se elimina
        assertEquals(0, bc.montoMedioUltimoBloque());
        assertEquals(1, bc.maximoTenedor()); // ambos saldos vuelven a 0
    }

    @Test
    public void testSoloTransaccionesDeCreacion() { // me aseguro que montoMedioUltimoBloque devuelve 0
        Berretacoin bc = new Berretacoin(3);
        Transaccion[] trans = {
            new Transaccion(1, 0, 1, 100),
            new Transaccion(2, 0, 2, 200)
        };
        bc.agregarBloque(trans);

        assertEquals(0, bc.montoMedioUltimoBloque());
        assertEquals(2, bc.maximoTenedor());
    }

    @Test
    public void testMayorValorEmpatePorId() { // verifico que txMayorValorUltimoBloque elige el id mayor cuando hay empate
        Berretacoin bc = new Berretacoin(2);
        Transaccion[] trans = {
            new Transaccion(5, 1, 2, 100),
            new Transaccion(6, 1, 2, 100) // id mayor
        };
        bc.agregarBloque(trans);

        Transaccion mayor = bc.txMayorValorUltimoBloque();
        assertEquals(6, mayor.id());
    }

    @Test
    public void testHackearTransaccionDeCreacion() { // verifico que hackearTx revierte la transacción de creación y se actualiza bien el heap
        Berretacoin bc = new Berretacoin(2);
        Transaccion[] trans = {
            new Transaccion(1, 0, 2, 500), // creación
            new Transaccion(2, 1, 2, 50)
        };
        bc.agregarBloque(trans);
        bc.hackearTx(); // acá se elimina la de creación

        assertEquals(1, bc.txUltimoBloque().length);
        assertEquals(2, bc.maximoTenedor()); // verifico que siga teniendo 50
        assertEquals(50, bc.montoMedioUltimoBloque()); // verifico que cuente solo la transacción de id 2
    }

    @Test
    public void testMultiplesBloques() { // verifico que las operaciones se hagan solamente sobre el último bloque
        Berretacoin bc = new Berretacoin(2);

        Transaccion[] bloque1 = {
            new Transaccion(1, 0, 1, 100)
        };

        Transaccion[] bloque2 = {
            new Transaccion(2, 1, 2, 50)
        };

        bc.agregarBloque(bloque1);
        bc.agregarBloque(bloque2);

        assertEquals(50, bc.montoMedioUltimoBloque()); // verifico que cuente solo la transacción de bloque 2
        bc.hackearTx(); // verifico que se tome solo en bloque 2
        assertEquals(1, bc.maximoTenedor()); // verifico que cuente la transacción de bloque 1
    }

    @Test
    public void testHackearHastaVaciar() { // verifico cómo funciona hackearTx vaciando un bloque
        Berretacoin bc = new Berretacoin(2);
        Transaccion[] trans = {
            new Transaccion(1, 1, 2, 10),
            new Transaccion(2, 1, 2, 20),
            new Transaccion(3, 1, 2, 30)
        };
        bc.agregarBloque(trans);

        bc.hackearTx(); // elimina 30
        bc.hackearTx(); // elimina 20
        bc.hackearTx(); // elimina 10
        bc.hackearTx(); // ya no hay más que eliminar, se vació el bloque

        assertEquals(0, bc.txUltimoBloque().length);
    }

    @Test
    public void testUnicoUsuarioConCreacion() { // inicializo con un solo usuario
        Berretacoin bc = new Berretacoin(1);
        Transaccion[] trans = {
                new Transaccion(0, 0, 1, 999)
        };
        bc.agregarBloque(trans);

        assertEquals(1, bc.maximoTenedor());
        assertEquals(0, bc.montoMedioUltimoBloque()); // no cuenta creaciones
    }

    @Test
    public void testTransaccionConMontoCero() { // verifico que no se rompa el heap ni haya error de división por 0 en el promedio
        Berretacoin bc = new Berretacoin(2);
        Transaccion[] trans = {
                new Transaccion(1, 1, 2, 0)
        };
        bc.agregarBloque(trans);

        assertEquals(0, bc.montoMedioUltimoBloque());
        assertEquals(1, bc.maximoTenedor()); // empate, menor id
    }

    @Test
    public void testPromedioDespuesDeHackearTodo() { // verifico que después de hackeear el montoMedio sea 0 porque no hay transacciones
        Berretacoin bc = new Berretacoin(2);
        Transaccion[] trans = {
                new Transaccion(1, 1, 2, 100)
        };
        bc.agregarBloque(trans);
        bc.hackearTx();

        assertEquals(0, bc.montoMedioUltimoBloque());
    }

    @Test
    public void testTransaccionConMismoUsuario() {
        Berretacoin bc = new Berretacoin(1);
        Transaccion[] trans = {
                new Transaccion(1, 1, 1, 50)
        };
        bc.agregarBloque(trans);

        assertEquals(50, bc.montoMedioUltimoBloque());
        assertEquals(1, bc.maximoTenedor());
    }

    @Test
    public void testMaximoTenedorRepetido() {
        Berretacoin bc = new Berretacoin(2);
        Transaccion[] trans = {
                new Transaccion(1, 1, 2, 50)
        };
        bc.agregarBloque(trans);

        int t1 = bc.maximoTenedor();
        int t2 = bc.maximoTenedor();
        int t3 = bc.maximoTenedor();

        assertEquals(t1, t2);
        assertEquals(t2, t3);
    }


}
