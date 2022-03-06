# atracabank-jpa-hibernate-console

Monitorización y control de atracos a sucursales bancarias.

<div align="center" markdown="1">

![La monitorizacin de atracos. Diagramas](screenshot.png)

</div>

## Requisitos

En los últimos años, el número de robos, atracos y ataques en general a distintas sucursales bancarias ha ascendido notablemente. Para ello nos encargan una aplicación informática para la monitorización y control.

Para comenzar con la aplicación, se tendrá en cuenta:

- Existirán entidades bancarias, que serán identificadas mediante un código único.
- Cada entidad bancaria tendrá sucursales en distintas ciudades, que se identificarán mediante un código. Cada sucursal tendrá una dirección, un número de trabajadores o empleados y un director. Se debe tener en cuenta que existen entidades bancarias sin sucursales físicas.
- Cada sucursal contratará a los vigilantes que consideren oportunos, pudiendo éstos tener vinculación con más de una sucursal. Un vigilante tendrá un código que lo identifique inequívocamente. La relación entre las sucursales y los miembros de seguridad se realizará mediante contratos, que tendrán un identificador, una fecha de comienzo y una fecha de fin. Además, en el contrato se especificará si se permite el uso de armas o no.
- La sucursal puede sufrir distintos altercados realizados por delincuentes, que tendrán a su vez un número de registro que les identifique. Los atracos se realizan en una fecha concreta, y traerán consigo una condena por los hechos. Cada uno de los atracos será juzgado por un Juez del que precisamos conocer su identidad.
- Alguna de las personas implicadas en los atracos pertenecen a bandas organizadas y por ello se desea saber a qué banda pertenecen. Dichas bandas se definen por un número de banda y por el número de miembros.
- Esta afiliación de delincuentes con bandas organizadas puede ser múltiple, es decir, que un delincuente puede estar en más de una banda. A su vez, una banda organizada estará formada por dos miembros como mínimo.
- Procederemos también a crear una jerarquía de clases en la que se permitan distintos tipos de empleado, que perteneceran a un banco, y sólo a uno; es decir, un empleado no puede trabajar en varios bancos. Dividiremos entre directivos y administrativos.
    - Nombre, Apellido, DNI, nivel (un número de 1 a 3) y cargo (director, subdirector, gerente...) son los campos que definen a un empleado de tipo directivo.
    - Para los administrativos se desea guardar los datos de Nombre, Apellido, DNI, número de la seguridad social o NSS y el número de cuenta o IBAN.

Se pide:

1. Diagrama UML de entidad - relación del modelo de dominio.
2. Diagrama UML de base de datos SQL y su correspondiente script de creación de tablas.
3. Diagrama UML de clases así como hacer una pequeña aplicación de consola que permita hacer las operaciones CRUD básicas sobre las entidades del modelo de dominio.
