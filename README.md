# atracabank-jpa-hibernate-console

Monitorización y control de atracos a sucursales bancarias.

## Requisitos

En los últimos años, el número de robos, atracos y ataques en general a distintas sucursales bancarias ha ascendido notablemente. Para ello nos encargan una aplicación informática para la monitorización y control.

Para comenzar con la aplicación, se tendrá en cuenta:

- Existirán entidades bancarias, que serán identificadas mediante un código único.
- Cada entidad bancaria tendrá sucursales en distintas ciudades, que se identificarán mediante un código. Cada sucursal tendrá una dirección, un número de trabajadores o empleados y un director. Se debe tener en cuenta que existen entidades bancarias sin sucursales físicas.
- Cada sucursal contratará a los vigilantes que consideren oportunos, pudiendo éstos tener vinculación con más de una sucursal. Un vigilante tendrá un código que lo identifique inequívocamente.
- La sucursal puede sufrir distintos altercados realizados por delincuentes, que tendrán a su vez un número de registro que les identifique.
- Alguna de las personas implicadas en los atracos pertenecen a bandas organizadas y por ello se desea saber a qué banda pertenecen. Dichas bandas se definen por un número de banda y por el número de miembros.

Se pide:

1. Diagrama UML de entidad - relación del modelo de dominio.
2. Diagrama UML de base de datos SQL y su correspondiente script de creación de tablas.
3. Diagrama UML de clases así como hacer una pequeña aplicación de consola que permita hacer las operaciones CRUD básicas sobre las entidades del modelo de dominio.
