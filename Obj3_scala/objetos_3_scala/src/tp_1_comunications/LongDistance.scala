package tp_1_comunications

import java.util.Date
import tp_1.Table

case class LongDistance(override val destinationNumber: Int, override val duration: Int, origen: String, destino: String, override val date: Date) extends Call(destinationNumber, duration, date) {
	def minuteCost(tabla: Table): Double = {
		tabla costo_desde_hasta (origen, destino)
	}
}
