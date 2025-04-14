**agregarBloque(b: Secuencia(Transacción))** [b = bloque = secuencia de transacciones]

## Precondiciones:

- cantidad de transacciones válidas: 0 < |b| ≤ 50 -> cada bloque contiene entre 1 y 50 transacciones
- transacciones con IDs ordenados dentro del bloque: (∀i ∈ [1..#transacciones(b)])(id(transacciones(b)[i−1]) < id(transacciones(b)[i])
- cada transacción tiene:
	- id_transacción: entero positivo
    - id_comprador ≠ id_vendedor, ambos enteros positivos
    - monto: entero positivo
- como máximo una transacción de creación por bloque: si existe, debe ser la primera (posición 0) y cumplir: id_comprador = 0 y id_vendedor ≠ 0. solo permitida si el total emitido < 3000 ->  esTransacciónDeCreación(t) <=> comprador(t) = 0 ∧ vendedor(t) ≠ 0
- el comprador tiene dinero suficiente en todas las transacciones que no sean de creación?: se verifica que cada id_comprador (≠ 0) tenga al menos el monto correspondiente en su balance antes de gastar

## Postcondiciones:

- se agrega un nuevo bloque a la cadena, con identificador igual a id del último bloque + 1.
- los saldos de los users se actualizan:
	- se suma al saldo del vendedor el monto de cada transacción.
	- se resta del saldo del comprador el monto (si id_comprador ≠ 0).
- si hay una transacción de creación, se emite nueva moneda:
	- se incrementa el total emitido en la cantidad que corresponda
	- se suma el mismo monto al saldo del vendedor
- condiciones más generales del sistema:
	- nadie puede gastar más de lo que tiene
	- no se supera el límite de emisión de 3000 unidades
	- se preserva la secuencia de bloques de forma consecutiva

---

## Generador: 

**transacción: nat x nat x nat x nat -> Transacción** [(id, id_comprador, id_vendedor, monto)]

---

## Observadores:

- **idBloque: Bloque -> nat**
- **transacciones: Bloque -> Secuencia(Transacción)**

### Ejemplo de uso:

- b = bloque(3, t1, t2, t3)

- transacciones(b) = < 3, t1, t2, t3 >, donde < t1, t2, t3 > es una secuencia de 4 transacciones (por ejemplo t1 = transacción(0, 0, 5, 100) y así)

---

## Rec TADs
- **[Apunte de TADs](https://github.com/blatth/uba-algo2/blob/main/Apuntes/TADs.pdf)**

Cada función debe indicar la precondición y la postcondición (requiere y asegura). Como ya indicamos, los requiere y asegura van a hacer referencia a los **observadores** del TAD. Para referirnos al **observador** x de la instancia t usamos la notación t.x. Para hablar del valor inicial de un parámetro de tipo inout usamos metavariables, refiriendo al tipo entero, no al **observador**, es decir, T_0.x, no t.X_0. Las metavariables deben declararse en el requiere.
Para que la especificación sea completa, tenemos que describir el estado de todos los **observadores** al _salir_ de la operación. Recordemos que la implementación final del TAD solo debe respetar la especificación y puede modificar cualquier cosas que no se defina.

---

	[RENOMBRE DE TIPOS]
 
Transacción = struct {
  id_transaccion: ℤ
  id_comprador: ℤ
  id_vendedor: ℤ
  monto: ℤ
}
 
Bloque = struct {
  id: ℤ
  transacciones: seq<Transacción>
}
 
TAD $Berretacoin {
  obs cadenaBloques: seq<Bloque>
  obs tenedores: dict<Z, Z>
  obs totalCreado: Z
 
	[CONSTRUCTOR]
 
  proc berretacoinNuevo(): $Berretacoin{
    asegura {
    res.cadenaBloques = ⟨⟩ ∧
    res.tenedores = {} ∧
    res.totalCreado = 0
    	}
	}
 
  proc agregarBloque(inout bc: $Berretacoin, in trnsc: seq<Transacción>){
    requiere {
	cadenaBloques = cadenaBloques₀ ∧
	totalCreado = totalCreado₀ ∧
	tenedores = tenedores₀
		}
	requiere {
    |trnsc| ≤ 50 ∧ --> cada bloque puede tener como máximo 50 transacciones
    ∀ t ∈ trnsc: t.monto > 0 ∧ t.id_comprador ≠ t.id_vendedor ∧ --> no se permiten transacciones con montos negativos ni cero ∧ el comprador y el vendedor deben ser distintos
    (trnsc ≠ [] → (trnsc[0].id_comprador = 0 ↔ bc.totalCreado < 3000)) ∧ --> controla si se puede incluir una transacción de creación: si trnsc no está vacía, la primera transacción es de creación y verifica que todavía se pueden emitir monedas
    ∀ t ∈ trnsc: t.id_comprador ≠ 0 → t.id_comprador ∈ bc.tenedores ∧ bc.tenedores[t.id_comprador] ≥ t.monto --> controla que ningún comprador (salvo el 0 que es de creación) intente gastar más de lo que tiene: si id ≠ 0, t.id_comprador debe estar en el diccionario de tenedores y Y su saldo debe ser mayor o igual al monto de la transacción
    	}
    asegura { ---> se agrega el bloque ∧ se actualiza el total creado ∧ se actualizan los saldos 
    bc.cadenaBloques = append(cadenaBloques₀, nuevoBloque) ∧
    bc.totalCreado = (totalCreado₀ + montoCreado ∧ bc.totalCreado ≤ 3000) ∧
    bc.tenedores = actualizarSaldos(tenedores₀, trnsc) --> habría que definirla
    	}
	}
}


---- VERSIÓN CON AUXILIARES

	[RENOMBRE DE TIPOS]
 
Transacción = struct {
  id_transaccion: Z
  id_comprador: Z
  id_vendedor: Z
  monto: Z
}
 
Bloque = struct {
  id: Z
  transacciones: seq<Transacción>
}
 
TAD $Berretacoin {
  obs cadenaBloques: seq<Bloque>
  obs tenedores: dict<Z, Z> --> key = id del usuario, value = saldo actual. sirve para, con actualizarSaldo, hacer un acumulador y ya tener el dict<id_usuario, saldo>
  obs totalCreado: Z --> para limitar que el totalCreado de monedas no sea > 3000
 }

	[CONSTRUCTOR]
 
  proc berretacoinNuevo(): $Berretacoin{
    asegura {
    res.cadenaBloques = ⟨⟩ ∧
    res.tenedores = {} ∧
    res.totalCreado = 0
    	}
	}

proc agregarBloque(inout bc: $Berretacoin, in trnsc: seq<Transacción>) {
    
    requiere {
        bc = bc₀ ∧

        |trnsc| ≤ 50 ∧
        transaccionesValidas(trnsc, bc₀.tenedores) ∧
        creacionPermitida(trnsc, bc₀.totalCreado)
    }

    asegura {
        bc.cadenaBloques = append(bc₀.cadenaBloques, nuevoBloque) ∧

        bc.totalCreado = bc₀.totalCreado + montoCreado(trnsc) ∧ bc.totalCreado ≤ 3000 ∧

        bc.tenedores = actualizarSaldos(bc₀.tenedores, trnsc)
    }
}

en el asegura se agrega el bloque ∧ se actualiza el total creado ∧ se actualizan los saldos 


aux montoCreado(trnsc: seq<Transacción>) : Z = ----> devuelve el monto de la transacción de creación si está presente al inicio, o 0 si no hay.
    IfThenElse(|trnsc| > 0 ∧ trnsc[0].id_comprador = 0, trnsc[0].monto, 0)

devuelve el monto monto de la primera transacción del bloque SOLO si es de creación (id_comprador = 0), sino devuelve 0. es como un verificador de creaciones, para usar el totalCreado de acumulador y que no se pase de 3000  

pred transaccionesValidas(trnsc: seq<Transacción>, tenedores: dict<ℤ, ℤ>) {
    (∀ t ∈ trnsc)(t.monto > 0 ∧ t.id_comprador ≠ t.id_vendedor ∧ (t.id_comprador ≠ 0 → (t.id_comprador ∈ tenedores ∧ tenedores[t.id_comprador] ≥ t.monto)))
}

no se permite mover 0 o una cantidad negativa ∧ no puede ser una transacción “con uno mismo” ∧ si el comprador no es el 0 (o sea, no es una transacción de creación), entonces: debe existir en el diccionario tenedores y debe tener suficiente (mayor o igual) saldo para la transacción

pred creacionPermitida(trnsc: seq<Transacción>, totalCreado: ℤ) {
    (|trnsc| = 0) ∨ (trnsc[0].id_comprador = 0 ↔ totalCreado < 3000)
}

verifica si no hay transacciones (no hay problema en este caso) ∨ si hay transacciones, la primera transacción puede ser de creación (id_comprador = 0) si y solo si todavía no se crearon 3000 monedas (totalCreado < 3000). si la primera transacción no es de creación, no le interesa y no impide otras transacciones, por lo que no se especifica
es  "↔ totalCreado < 3000" porque si se crean "↔ totalCreado ≤ 3000", se está permitiendo crear un bloque de más, porque cuando se verifica que = 3000, se puede crear un bloque más (creo)

----

[agregarBLOQUE]

el segundo requiere me convence más porque si el comprador es 0 no le verifica el saldo

[hayIDSREPETIDOS]

hayIdsRepetidos me parece mejor porque es más "directa" y no hace falta usar el esValida

[proposición de NUEVO ASEGURA maximosTENEDORES]

requiere{bc.bloques ≠ {}}
asegura {
  (∀id0 : id_comprador) (id0 ∈ res ↔ esUsuario(id0, bc.bloques)) ∧
  (∀id1 : id_comprador) (esUsuario(id1, bc.bloques)) -> calculaSaldo(id0, bc.bloques, ⟨⟩) ≥ calculaSaldo(id1, bc.bloques, ⟨⟩)) ∧ 
  ¬hayIdsRepetidos(res)
}

- siento que es más "directo" y mas legible, creo que cubre todo lo mismo que el asegura que tenemos ahora tomando directamente los ids y no una secuencia de ids
- acá el calculaSaldo lo puse con seq vacías en la pos 2 de la tupla porque no sé si vamos a eliminar el st del calculaSaldo
- nos ahorraríamos de hacer el tieneMasOIgualQueTodos y se reutiliza lo del ej1