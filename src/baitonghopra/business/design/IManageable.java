package baitonghopra.business.design;

import java.util.Scanner;

public interface IManageable
{
    void showManageMenu(Scanner scanner);

    void addItem(Scanner scanner);//Thêm các mục mới

    void displayAllItem();//Hiển thị tất cả các mục có trong danh sách

    void updateItemById(Scanner scanner);//Update thông tin cho từng mục

    void deleteItem(Scanner scanner);//Xóa mục ra khỏi mảng

    int searchById(Scanner scanner);//Lấy về index cần tìm
}
