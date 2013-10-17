package tp_1

import java.util.Calendar
import java.util.Date
import tp_1_comunications.Call
import tp_1_comunications.LocalCall
import tp_1_comunications.Comunication
import tp_1_comunications.LongDistance
import tp_1_comunications.Message

case class TalkEverything() extends Plan {
	override def cost(comunication: Comunication, table: Table): Double = comunication match {
		case message: Message => message.cost(table)
		case call: Call => costMinusDuration(call, table)
	}
	def costMinusDuration(call: Call, tabla: Table): Double = {
		if (call.date.getDay() == Calendar.SATURDAY || call.date.getDay() == Calendar.SUNDAY) {
			if (call.duration < 5) { 0.0 } else {
				call match {
					case LocalCall(destinationNumber: Int, duration: Int, date: Date) =>
						LocalCall(destinationNumber, duration - 5, date).cost(tabla)
					case LongDistance(destinationNumber: Int, duration: Int, from: String, to: String, date: Date) =>
						LongDistance(destinationNumber, duration - 5, from, to, date).cost(tabla)

				}
			}

		} else {
			call.cost(tabla)
		}
	}

}
