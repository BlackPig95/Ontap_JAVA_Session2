package baitonghopra.business.design;

import baitonghopra.business.entity.Product;

import java.util.Scanner;

public interface IProduct extends IManageable, ISearchable
{
    void sortByPrice(Scanner scanner);
}
