package com.example.server.database.factory;

import com.example.server.entities.Category;
import com.example.server.entities.FormOfPayment;

public interface IFormOfPayment {
    FormOfPayment selectById(int id);

}
