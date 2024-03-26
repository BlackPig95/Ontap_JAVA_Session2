package baitonghopra.business.implement;

import baitonghopra.business.design.ICategory;
import baitonghopra.business.entity.Categories;
import baitonghopra.business.entity.Product;

import java.util.Scanner;

import static baitonghopra.business.implement.ProductManagement.productList;

public class CategoryManagement implements ICategory
{
    static final Categories[] categoriesList = new Categories[100];

    @Override
    public void showManageMenu(Scanner scanner)
    {
        while (true)
        {
            System.out.println("********************CATEGORIES MENU***********\n" +
                    "1. Nhập thông tin các danh mục\n" +
                    "2. Hiển thị thông tin các danh mục\n" +
                    "3. Cập nhật thông tin danh mục\n" +
                    "4. Xóa danh mục\n" +
                    "5. Cập nhật trạng thái danh mục\n" +
                    "6. Quay lại");
            System.out.println("Nhập lựa chọn theo danh sách trên");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice)
            {
                case 1:
                    addItem(scanner);
                    break;
                case 2:
                    displayAllItem();
                    break;
                case 3:
                    updateItemById(scanner);
                    break;
                case 4:
                    deleteItem(scanner);
                    break;
                case 5:
                    updateStatus(scanner);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Lựa chọn không khả dụng");
                    break;
            }
        }
    }

    @Override
    public void addItem(Scanner scanner)
    {
        System.out.println("Nhập số lượng danh mục muốn thêm mới");
        int numberOfCategories = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numberOfCategories; i++)
        {
            Categories newCat = new Categories();
            newCat.inputData(scanner, categoriesList, true);
            for (int j = 0; j < categoriesList.length; j++)
            {
                if (categoriesList[j] == null)
                {
                    categoriesList[j] = newCat;
                    break;
                }
            }
            System.out.println("Thêm sản phẩm thành công");
        }
    }

    @Override
    public void displayAllItem()
    {
        if (checkListNull())
            return;
        showCurrentList();
    }

    @Override
    public void updateItemById(Scanner scanner)
    {
        if (checkListNull())
            return;
        int updateIndex = searchById(scanner);
        if (updateIndex != -1)//Nếu tìm thấy thì thực hiện cập nhật
        {
            System.out.println("Thông tin cũ");
            categoriesList[updateIndex].displayData();
            outer:
            while (true)
            {
                System.out.println("Lựa chọn phương án cập nhật");
                System.out.println("1. Cập nhật tên danh mục");
                System.out.println("2. Cập nhật mô tả danh mục");
                System.out.println("3. Cập nhật trạng thái danh mục");
                System.out.println("4. Cập nhật tất cả");
                System.out.println("5. Thoát");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice)
                {
                    case 1:
                        System.out.println("Nhập tên mới");
                        String newName = scanner.nextLine();
                        categoriesList[updateIndex].setCatalogName(newName);
                        System.out.println("Tên đã cập nhật " + categoriesList[updateIndex].getCatalogName());
                        break;
                    case 2:
                        System.out.println("Nhập mô tả mới");
                        String newDescription = scanner.nextLine();
                        categoriesList[updateIndex].setDescription(newDescription);
                        System.out.println("Mô tả đã cập nhật " + categoriesList[updateIndex].getDescription());
                        break;
                    case 3:
                        categoriesList[updateIndex].setCatalogStatus(!categoriesList[updateIndex].isCatalogStatus());
                        System.out.println("Trạng thái đã cập nhật "
                                + (categoriesList[updateIndex].isCatalogStatus() ? "Hoạt động" : "Không hoạt động"));
                        break;
                    case 4:
                        System.out.println("Mời nhập thông tin mới cho danh mục này");
                        categoriesList[updateIndex].inputData(scanner, categoriesList, false);
                        System.out.println("Cập nhật thành công, thông tin mới như sau:");
                        categoriesList[updateIndex].displayData();
                        break;
                    case 5:
                        break outer;//Phá vòng while
                    default:
                        System.out.println("Lựa chọn không khả dụng");
                        break;
                }
            }
            return;
        }
        System.out.println("Không tìm thấy danh mục yêu cầu");
    }

    @Override
    public void deleteItem(Scanner scanner)
    {
        if (checkListNull())
            return;
        int deleteIndex = searchById(scanner);
        if (deleteIndex == -1)
        {
            System.out.println("Không tìm thấy danh mục yêu cầu");
            return;
        }
        if (productList[0] != null)//Nếu có phần tử trong mảng sản phẩm thì duyệt để kiểm tra
        {
            for (int i = 0; i < productList.length; i++)//Duyệt mảng sản phẩm
            {   //Nếu tìm thấy sản phẩm trong danh mục này thì không xóa
                if (productList[i].getCatalogId() == categoriesList[deleteIndex].getCatalogId())
                {
                    System.out.println("Danh mục này có sản phẩm nên không thể xóa");
                    return;
                }
            }
        }
        //Bắt đầu duyệt từ vị trí phần tử cần xóa
        for (int j = deleteIndex; j < categoriesList.length - 1; j++)
        {
            if (categoriesList[j] == null)
                break;//Khi chạm đến phần tử cuối thì ngừng duyệt
            //Shift elemement sang trái để ghi đè lên phần tử cần xóa
            categoriesList[j] = categoriesList[j + 1];
        }
        System.out.println("Xóa danh mục thành công");
    }

    @Override
    public void updateStatus(Scanner scanner)
    {
        int statusIndex = searchById(scanner);
        if (statusIndex == -1)
        {
            System.out.println("Không tìm thấy danh mục yêu cầu");
            return;
        }
        System.out.println("Trạng thái cũ: " + (categoriesList[statusIndex].isCatalogStatus()
                ? "Hoạt động" : "Không hoạt động"));
        //Set trạng thái ngược lại của trạng thái trước đó
        categoriesList[statusIndex].setCatalogStatus(!categoriesList[statusIndex].isCatalogStatus());
        System.out.println("Trạng thái mới: " + (categoriesList[statusIndex].isCatalogStatus()
                ? "Hoạt động" : "Không hoạt động"));
    }

    private boolean checkListNull()
    {
        if (categoriesList[0] == null) //Trường hợp chưa thêm phần tử nào
        {
            System.out.println("Hiện chưa có danh mục nào");
            return true;
        }
        return false;
    }

    @Override
    public int searchById(Scanner scanner)//Trả về index của phần tử cần tìm
    {
        System.out.println("Danh sách các danh mục hiện có");
        showCurrentList();
        System.out.println("Mời lựa chọn danh mục bằng cách nhập mã danh mục");
        int updateId = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < categoriesList.length; i++)
        {
            if (categoriesList[i] == null)//Tránh duyệt phần tử null
                return -1;
            if (categoriesList[i].getCatalogId() == updateId)
            {
                return i;
            }
        }
        return -1;
    }

    private void showCurrentList()
    {
        for (Categories categories : categoriesList)
        {
            if (categories == null)//Tránh việc duyệt phần tử null
                break;
            categories.displayData();
        }
    }
}
