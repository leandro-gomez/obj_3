package tp_2

import tp_1.{ Plan, Table }
import tp_1_comunications.{ Call, LocalCall, LongDistance, Message, Comunication }
import java.util.Date

class Cliente(var name: String, var planes: List[Plan]) {

	var comunicaciones = List[Comunication]()

	def costo(comunicacion: Comunication, tabla: Table): Double = {
		costoPartial(comunicacion, tabla, planes.tail, planes.head.cost(comunicacion, tabla))
	}

	def costoPartial(comunicacion: Comunication, tabla: Table, planes: List[Plan], costo: Double): Double = planes match {
		case (plan: Plan) :: (plan2: Plan) :: x => if (costo == 0) { 0.0 } else { costoPartial(comunicacion, tabla, planes.tail, plan.cost(comunicacion, tabla)) }
		case (plan: Plan) :: Nil => if (costo == 0) { 0.0 } else { plan.cost(comunicacion, tabla) }
		case _ => costo
	}

	def addPlane(plan: Plan) {
		if (planes.size > 5) {
			planes = (plan :: (planes.reverse tail)) reverse
		} else {
			planes = plan :: planes
		}
	}

	def costo_del_mes(mes: Date, tabla: Table): Double = {
		comunicaciones.foldLeft(0.0)((a, b) => a + costo(b, tabla))
	}

	def addComunication(comunicacion: Comunication) {
		this.comunicaciones = comunicacion :: this.comunicaciones
	}

	def llamadas(): List[Call] = {
		comunicaciones collect { case x: Call => x }
	}

	def locales(): List[LocalCall] = {
		comunicaciones collect { case x: LocalCall => x }
	}

	def largas_distancias(): List[LongDistance] = {
		comunicaciones collect { case x: LongDistance => x }
	}

	def mensajes: List[Message] = {
		comunicaciones collect { case x: Message => x }
	}

	def llamadas_del_mes(mes: Date): List[Call] = {
		this.llamadas filter (_.date.getMonth == mes.getMonth)
	}

	def comunicaciones_largas(mes: Date): List[Comunication] = {
		this.comunicaciones filter (_ is_long)

	}
	def count_comunicaciones_largas(mes: Date): Int = {
		comunicaciones_largas(mes) length
	}

	def comunicaciones_del_mes(mes: Date): List[Comunication] = {
		comunicaciones filter (_.date.getMonth == mes.getMonth)
	}

	def comunicaciones_del_dia(mes: Date): List[Comunication] = {
		comunicaciones_del_mes(mes) filter (_.date.getDate == mes.getDate)
	}

	def minutos_del_mes(mes: Date): Double = {
		llamadas_del_mes(mes).foldLeft(0.0)((suma, comunicacion) => suma + comunicacion.duration)
	}

}