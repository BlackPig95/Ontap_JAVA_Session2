package baitonghopra.run;

import baitonghopra.business.design.ICategory;
import baitonghopra.business.design.IProduct;
import baitonghopra.business.entity.Categories;
import baitonghopra.business.entity.Product;
import baitonghopra.business.implement.CategoryManagement;
import baitonghopra.business.implement.ProductManagement;

import java.util.Scanner;

public class ShopManagement
{
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        ICategory categoryManager = new CategoryManagement();
        IProduct productManager = new ProductManagement();
        while (true)
        {
            System.out.println("****************SHOP-MENU********************");
            System.out.println("1. Quản lý danh mục");
            System.out.println("2. Quản lý sản phẩm");
            System.out.println("3. Thoát");
            System.out.println("Nhập lựa chọn theo danh sách trên");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice)
            {
                case 1:
                    categoryManager.showManageMenu(scanner);
                    break;
                case 2:
                    productManager.showManageMenu(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Lựa chọn không khả dụng");
                    break;
            }
        }
    }
}
