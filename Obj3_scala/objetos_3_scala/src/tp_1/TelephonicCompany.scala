package tp_1

import java.util.Calendar
import tp_1_comunications.Message

object TelephonicCompany {

	def main(strings: Array[String]) {
		var company = new Company()
		var leandro = Client("leandro", NormalPlan())
		var julian = Client("julian", NormalPlan())
		var ivan = Client("ivan", NormalPlan())
		var daniel = Client("daniel", NormalPlan())

		company addClient (leandro)
		company addClient (julian)
		company addClient (ivan)
		company addClient (daniel)

		//Ivan
		//Locales = 0
		//Larga Distancias = 1 (normal)
		//Mensajes = 20 (15 normales y 5 largos)
		var hora_modelo = Calendar getInstance () getTime ()
		(0 until 16) foreach ((num: Int) => {
			var hora = Calendar getInstance () getTime ()
			hora setHours (hora_modelo.getHours() + num)
			ivan addComunication (Message(12345678, 20, hora))
		})
		(0 until 6) foreach ((num: Int) => {
			var hora = Calendar getInstance () getTime ()
			hora setHours (hora_modelo.getHours() + num)
			ivan addComunication (Message(12345678, 200, hora))
		})
		println("minutos de " + ivan.name + ": " + company.minutesOfMonth(ivan, hora_modelo))

	}

}