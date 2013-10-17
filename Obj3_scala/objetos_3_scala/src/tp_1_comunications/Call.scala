package tp_1_comunications

import java.util.Date
import tp_1.Table

abstract case class Call(override val destinationNumber: Int, duration: Int, override val date: Date) extends Comunication(destinationNumber, date) {
	def is_long(): Boolean = {
		duration > 60 * 5
	}

	def minuteCost(tabla: Table): Double

	def cost(table: Table): Double = {
		by_time(table)
	}

	def by_time(table: Table): Double = {
		if (duration <= 60)
			return minuteCost(table)
		else {
			return minuteCost(table) + minuteExceedMinutoCost(table)
		}
	}

	def minuteExceedMinutoCost(table: Table) = exceedFractions() * exceedFractionsCost(table)
	def exceedFractions() = ((duration - 60) / 10)
	def exceedFractionsCost(table: Table) = minuteCost(table)

}