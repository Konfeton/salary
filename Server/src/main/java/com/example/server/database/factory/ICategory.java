package com.example.server.database.factory;

import com.example.server.entities.Category;

public interface ICategory {
    Category selectById(int id);
}
