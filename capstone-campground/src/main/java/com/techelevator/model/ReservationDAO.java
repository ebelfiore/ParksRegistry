package com.techelevator.model;

import java.math.BigDecimal;

public interface ReservationDAO {
	
public int searchReservation(String camp, String arrive, String depart, BigDecimal cost);

}
