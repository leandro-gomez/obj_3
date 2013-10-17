package tp_1_comunications

import java.util.Date
import tp_1.Table

case class LocalCall(override val destinationNumber: Int, override val duration: Int, override val date: Date) extends Call(destinationNumber, duration, date) {
	def minuteCost(tabla: Table): Double = {
		60
	}
}