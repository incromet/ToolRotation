| #CE |             Método             |                                                              Clase de Equivalencia                                                             |      Tipo     |                          Resultado                          |
|:---:|:------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------:|:-------------:|:-----------------------------------------------------------:|
|  1  | Controller.rotateSelectedShape | Segmento Horizontal: La instancia del controlador tiene un segmento de recta r tal que r.punto1.y==r.punto2.y ^ r.punto1.x != r.punto2.x       | QuickTheories | r.punto1.y!=r.punto2.y ^ r.punto1.x == r.punto2.x           |
|  2  | Controller.rotateSelectedShape | Segmento Vertical: La instancia del controlador tiene un segmento de recta r tal que r.punto1.y!=r.punto2.y ^ r.punto1.x == r.punto2.x         | QuickTheories | r.punto1.y==r.punto2.y ^ r.punto1.x != r.punto2.x           |
|  3  | Controller.rotateSelectedShape | Segmento Diagonal: La instancia del controlador tiene un segmento de recta r tal que r.punto1.y!=r.punto2.y ^ r.punto1.x != r.punto2.x         | QuickTheories | r.punto1.y!=r.punto2.y ^ r.punto1.x != r.punto2.x           |
|  4  | Controller.rotateSelectedShape | Segmento Afuera: La instancia del controlador tiene un segmento de recta r tal que  r.punto1 ^ r.punto2 < screenSize.height ^ screenSize.width |     Error     |  r.punto1 ^ r.punto2 < screenSize.height ^ screenSize.width |