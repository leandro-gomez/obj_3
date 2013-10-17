package tp_1

import java.util.Date
import tp_1_comunications.Call
import tp_1_comunications.LocalCall
import tp_1_comunications.Comunication
import tp_1_comunications.LongDistance
import tp_1_comunications.Message

case class Client(name: String, plan: Plan) {

	var comunications = List[Comunication]()

	def cost(comunicacion: Comunication, tabla: Table): Double = {
		plan.cost(comunicacion, tabla)
	}

	def costByMonth(date: Date, tabla: Table): Double = {
		comunications.foldLeft(0.0)((a, b) => a + cost(b, tabla))
	}

	def addComunication(comunication: Comunication) {
		this.comunications = comunication :: this.comunications
	}

	def calls(): List[Call] = {
		comunications collect { case x: Call => x }
	}

	def localCalls(): List[LocalCall] = {
		comunications collect { case x: LocalCall => x }
	}

	def longDistanceCalls(): List[LongDistance] = {
		comunications collect { case x: LongDistance => x }
	}

	def messages: List[Message] = {
		comunications collect { case x: Message => x }
	}

	def monthCalls(date: Date): List[Call] = {
		this.calls filter (_.date.getMonth == date.getMonth)
	}

	def longComunications(date: Date): List[Comunication] = {
		this.comunications filter (_ is_long)

	}
	def longComunicationsCallsCount(date: Date): Int = {
		longComunications(date) length
	}

	def comunicationByMonth(date: Date): List[Comunication] = {
		comunications filter (_.date.getMonth == date.getMonth)
	}

	def comunicationsByDay(date: Date): List[Comunication] = {
		comunicationByMonth(date) filter (_.date.getDate == date.getDate)
	}

	def minutesOfMonth(date: Date): Double = {
		monthCalls(date).foldLeft(0.0)((suma, comunicacion) => suma + comunicacion.duration)
	}

}