package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class HeapmaxTest {

    @Test
    public void testAgregarElementos() { //
        HeapMax heap = new HeapMax(2);

        heap.agregarElemento(1);
        heap.agregarElemento(7);

        assertEquals(7, heap.obtenerMaximo());

    }

    @Test
    public void testObtenerMaximo() { //
        HeapMax heap = new HeapMax(2);

        heap.agregarElemento(1);
        heap.agregarElemento(7);

        assertEquals(7, heap.obtenerMaximo());

    }

    @Test
    public void testObtenerMaximoString() { //
        HeapMax heap = new HeapMax(7);

        heap.agregarElemento("B");
        heap.agregarElemento("Z");
        heap.agregarElemento("G");

        assertEquals("Z", heap.obtenerMaximo());

    }

    @Test
    public void testSacarMaximo() { //
        HeapMax heap = new HeapMax(2);

        heap.agregarElemento(1);
        heap.agregarElemento(7);
        heap.sacarMaximo();
        heap.sacarMaximo();
        heap.agregarElemento(5);
        heap.agregarElemento(2);

        assertEquals(5, heap.sacarMaximo());

    }

    @Test
    public void testMultipleMaximo() { //
        HeapMax heap = new HeapMax(2);

        heap.agregarElemento(9);
        heap.agregarElemento(8);
        heap.sacarMaximo();
        heap.sacarMaximo();
        heap.sacarMaximo();
        heap.sacarMaximo();
        assertEquals(null, heap.obtenerMaximo());

    }

    @Test
    public void testSacarVacio() { //
        HeapMax heap = new HeapMax(5);

        heap.agregarElemento(10);
        heap.agregarElemento(47);
        heap.sacarMaximo();
        heap.sacarMaximo();
        heap.agregarElemento(5);
        heap.sacarMaximo();
        heap.agregarElemento(2);
        heap.sacarMaximo();

        assertEquals(null, heap.obtenerMaximo());

    }

    @Test
    public void testToString() { //
        HeapMax heap = new HeapMax(10);

        heap.agregarElemento(3);
        heap.sacarMaximo();
        heap.agregarElemento(5);
        heap.agregarElemento(77);
        heap.agregarElemento(9);
        heap.sacarMaximo();
        heap.agregarElemento(0);

        assertEquals("[9, 5, 0]", heap.toString());

    }

    @Test
    public void testStringVacio() { //
        HeapMax heap = new HeapMax(1);

        heap.sacarMaximo();
        heap.agregarElemento("sfdg");
        heap.sacarMaximo();

        assertEquals("[]", heap.toString());

    }

    @Test
    public void testDuplicados() {
        HeapMax<Integer> heap = new HeapMax<>(5);
        heap.agregarElemento(4);
        heap.agregarElemento(4);
        heap.agregarElemento(4);
        assertEquals(4, heap.obtenerMaximo());
        heap.sacarMaximo();
        assertEquals(4, heap.obtenerMaximo());
}


}