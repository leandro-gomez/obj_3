package tp_1_comunications

import java.util.Date
import tp_1.Table

abstract case class Comunication(destinationNumber: Int, date: Date) {
	def is_long(): Boolean
	def cost(tabla: Table): Double
}