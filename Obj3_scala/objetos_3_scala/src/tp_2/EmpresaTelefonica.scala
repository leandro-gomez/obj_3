package tp_2

import tp2_planes.NormalPlane
import java.util.Calendar

class EmpresaTelefonica {
	def bestCombination(client: Cliente, planes: List[NormalPlane]): List[NormalPlane] = {
		var mes = Calendar getInstance () getTime ()
		var mes_minus_one = Calendar getInstance () getTime ()
		mes_minus_one setMonth(mes_minus_one.getMonth  + 1)
		var mes_minus_two = Calendar getInstance() getTime()
		mes_minus_two setMonth((mes_minus_two.getMonth  + 1))
				
		
		
		List[NormalPlane]()		
	}
}