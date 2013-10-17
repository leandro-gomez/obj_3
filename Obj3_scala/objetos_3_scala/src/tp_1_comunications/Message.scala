package tp_1_comunications

import java.util.Date
import tp_1.Table

case class Message(override val destinationNumber: Int, length: Int, override val date: Date) extends Comunication(destinationNumber, date) {
	def is_long(): Boolean = {
		length > 140
	}

	def cost(table: Table): Double = {
		if (length < 140)
			return 12.0
		else {
			var partial = length / 140 * 12
			if (length % 140 > 0)
				partial += 12
			return partial
		}
	}

}