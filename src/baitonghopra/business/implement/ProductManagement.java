package baitonghopra.business.implement;

import baitonghopra.business.design.IProduct;
import baitonghopra.business.entity.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static baitonghopra.business.implement.CategoryManagement.categoriesList;

public class ProductManagement implements IProduct
{
    static final Product[] productList = new Product[100];

    @Override
    public void showManageMenu(Scanner scanner)
    {
        while (true)
        {
            System.out.println("*******************PRODUCT MANAGEMENT*****************\n" +
                    "1. Nhập thông tin các sản phẩm\n" +
                    "2. Hiển thị thông tin các sản phẩm\n" +
                    "3. Sắp xếp các sản phẩm theo giá\n" +
                    "4. Cập nhật thông tin sản phẩm theo mã sản phẩm\n" +
                    "5. Xóa sản phẩm theo mã sản phẩm\n" +
                    "6. Tìm kiếm các sản phẩm theo tên sản phẩm\n" +
                    "7. Tìm kiếm sản phẩm trong khoảng giá a – b\n" +
                    "8. Quay lại\n");
            System.out.println("Nhập lựa chọn theo danh sách trên");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice)
            {
                case 1:
                    if (categoriesList[0] == null)
                    {
                        System.out.println("Hiện không có danh mục nào, vui lòng thêm danh mục trước");
                        return;
                    }
                    addItem(scanner);
                    break;
                case 2:
                    displayAllItem();
                    break;
                case 3:
                    sortByPrice(scanner);
                    break;
                case 4:
                    updateItemById(scanner);
                    break;
                case 5:
                    deleteItem(scanner);
                    break;
                case 6:
                    searchByName(scanner);
                    break;
                case 7:
                    searchInRange(scanner);
                    break;
                case 8:
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
        System.out.println("Nhập số lượng sản phẩm muốn thêm mới");
        int numberOfProducts = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numberOfProducts; i++)
        {
            Product newProduct = new Product();
            System.out.println("Nhập thông tin của sản phẩm thứ " + (i + 1));
            newProduct.inputData(scanner, productList, categoriesList, -1);
            for (int j = 0; j < productList.length; j++)
            {   //Thêm sản phẩm mơi vào vị trí đang null => Đảm bảo mảng kéo dài liên tục
                if (productList[j] == null)
                {
                    productList[j] = newProduct;
                    break;
                }
            }
            System.out.println("Thêm sản phẩm thành công");
        }
    }

    @Override
    public void displayAllItem()
    {
        if (checkCurrentList())
            return;
        for (int i = 0; i < productList.length; i++)
        {
            if (productList[i] == null)
                break;
            productList[i].displayData();
        }
    }

    @Override
    public void updateItemById(Scanner scanner)
    {
        if (checkCurrentList())
            return;
        int updateIndex = searchById(scanner);
        if (updateIndex != -1)//Nếu tìm thấy thì thực hiện cập nhật
        {
            System.out.println("Thông tin cũ");
            productList[updateIndex].displayData();
            outer:
            while (true)
            {
                System.out.println("Lựa chọn phương án cập nhật");
                System.out.println("1. Cập nhật tên sản phẩm");
                System.out.println("2. Cập nhật mô tả sản phẩm");
                System.out.println("3. Cập nhật trạng thái sản phẩm");
                System.out.println("4. Cập nhật giá sản phẩm");
                System.out.println("5. Cập nhật danh mục sản phẩm");
                System.out.println("6. Cập nhật ngày thêm sản phẩm");
                System.out.println("7. Cập nhật tất cả");
                System.out.println("8. Thoát");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice)
                {
                    case 1:
                        System.out.println("Nhập tên mới");
                        String newName = scanner.nextLine();
                        productList[updateIndex].setProductName(newName);
                        System.out.println("Tên đã cập nhật: " + productList[updateIndex].getProductName());
                        break;
                    case 2:
                        System.out.println("Nhập mô tả mới");
                        String newDescription = scanner.nextLine();
                        productList[updateIndex].setDescription(newDescription);
                        System.out.println("Mô tả đã cập nhật: " + productList[updateIndex].getDescription());
                        break;
                    case 3:
                        int newStatus;
                        while (true)
                        {
                            System.out.println("Nhập vào trạng thái sản phẩm: 0 - Đang bán," +
                                    " 1 - Hết hàng, 2 - Không bán");
                            newStatus = Integer.parseInt(scanner.nextLine());
                            if (newStatus == 0 || newStatus == 1 || newStatus == 2)
                                break;
                            System.out.println("Lựa chọn không khả dụng, vui lòng nhập lại");
                        }
                        productList[updateIndex].setProductStatus(newStatus);
                        System.out.println("Trạng thái đã cập nhật: " + productList[updateIndex].getProductStatus());
                        break;
                    case 4:
                        System.out.println("Nhập giá mới");
                        productList[updateIndex].setPrice(Float.parseFloat(scanner.nextLine()));
                        System.out.println("Giá đã cập nhật: " + productList[updateIndex].getPrice());
                        break;
                    case 5:
                        System.out.println("Danh sách các danh mục hiện có");
                        for (int i = 0; i < categoriesList.length; i++)
                        {
                            if (categoriesList[i] == null)
                                break;
                            System.out.println(categoriesList[i].getCatalogId() + " : "
                                    + categoriesList[i].getCatalogName());
                        }
                        System.out.println("Cập nhật danh mục mới bằng mã danh mục ở trên");
                        productList[updateIndex].setCatalogId(Integer.parseInt(scanner.nextLine()));
                        System.out.println("Danh mục đã cập nhật: " + productList[updateIndex].getCatalogId());
                    case 6:
                        System.out.println("Nhập ngày mới");
                        Date newDate = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        boolean goodFormat = true;
                        try
                        {
                            newDate = sdf.parse(scanner.nextLine());
                        } catch (ParseException e)
                        {
                            System.out.println("Định dạng sai, không cập nhật được");
                            goodFormat = false;//Nếu nhập sai thì set flag về false để ngăn update
                        }
                        if (goodFormat)//Nhập đúng định dạng thì mới cho set lại date
                            productList[updateIndex].setCreatedDate(newDate);
                        System.out.println("Ngày của sản phẩm đã cập nhật: " + productList[updateIndex].getCreatedDate());
                        break;
                    case 7:
                        System.out.println("Mời nhập thông tin mới cho danh mục này");
                        productList[updateIndex].inputData(scanner, productList, categoriesList, updateIndex);
                        System.out.println("Cập nhật thành công, thông tin mới như sau:");
                        productList[updateIndex].displayData();
                        break;
                    case 8:
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
        if (checkCurrentList())
            return;
        int deleteIndex = searchById(scanner);
        if (deleteIndex == -1)
        {
            System.out.println("Không tìm thấy sản phẩm yêu cầu");
            return;
        }
        for (int i = deleteIndex; i < productList.length; i++)
        {
            if (productList[i] == null)
                break;//Khi chạm đến phần tử cuối thì ngừng duyệt
            //Shift elemement sang trái để ghi đè lên phần tử cần xóa
            productList[i] = productList[i + 1];
        }
        System.out.println("Xóa sản phẩm thành công");
    }

    @Override
    public void sortByPrice(Scanner scanner)
    {
        System.out.println("Nhập 1 nếu muốn sắp xếp tăng dần, 2 nếu muốn sắp xếp giảm dần");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice)
        {
            case 1:
                for (int i = 0; i < productList.length; i++)
                {
                    if (productList[i] == null)
                        break;
                    for (int j = 0; j < productList.length - i - 1; j++)
                    {   //Tránh null pointer khi đổi vị trí
                        if (productList[j + 1] == null)
                            break;
                        if (productList[j].getPrice() > productList[j + 1].getPrice())
                        {
                            Product temp = productList[j];
                            productList[j] = productList[j + 1];
                            productList[j + 1] = temp;
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i < productList.length; i++)
                {
                    if (productList[i] == null)
                        break;
                    for (int j = 0; j < productList.length - i - 1; j++)
                    {    //Tránh null pointer khi đổi vị trí
                        if (productList[j + 1] == null)
                            break;
                        if (productList[j].getPrice() < productList[j + 1].getPrice())
                        {
                            Product temp = productList[j];
                            productList[j] = productList[j + 1];
                            productList[j + 1] = temp;
                        }
                    }
                }
                break;
            default:
                System.out.println("Lựa chọn không khả dụng");
                break;
        }
    }

    @Override
    public void searchByName(Scanner scanner)
    {
        if (checkCurrentList())
            return;
        System.out.println("Mời nhập tên sản phẩm cần tìm");
        String searchName = scanner.nextLine().toLowerCase();
        boolean foundSimilar = false;
        for (int i = 0; i < productList.length; i++)
        {
            if (productList[i] == null)
                return;
            if (productList[i].getProductName().contains(searchName))
            {
                System.out.println("Tìm thấy sản phẩm có tên giống với mô tả");
                System.out.println(productList[i].getProductName());
                foundSimilar = true;
            }
        }
        if (!foundSimilar)
            System.out.println("Không tìm thấy sản phẩm nào giống với mô tả");
    }

    @Override
    public void searchInRange(Scanner scanner)
    {
        if (checkCurrentList())
            return;
        System.out.println("Nhập giá trị giới hạn dưới");
        float lowerLimit = Float.parseFloat(scanner.nextLine());
        float upperLimit;
        while (true)
        {
            System.out.println("Nhập giá trị giới hạn trên");
            upperLimit = Float.parseFloat(scanner.nextLine());
            if (upperLimit <= lowerLimit)
                System.out.println("Giới hạn trên phải lớn hơn giới hạn dưới");
            else break;
        }
        System.out.println("Danh sách các sản phẩm trong khoảng giá:");
        boolean foundProduct = false;//Kiểm tra xem có tìm thấy sản phẩm nào không
        for (int i = 0; i < productList.length; i++)
        {
            if (productList[i] == null)
                return;
            if (productList[i].getPrice() >= lowerLimit && productList[i].getPrice() <= upperLimit)
            {
                System.out.println(productList[i].getProductName());
                foundProduct = true;
            }
        }
        if (!foundProduct)
            System.out.println("Không có sản phẩm nào đạt yêu cầu");
    }

    private boolean checkCurrentList()
    {
        if (productList[0] == null)
        {
            System.out.println("Hiện không có sản phẩm nào");
            return true;
        }
        return false;
    }

    private void showCurrentList()
    {
        for (int i = 0; i < productList.length; i++)
        {
            if (productList[i] == null)//Tránh việc duyệt phần tử null
                break;
            productList[i].displayData();
        }
    }

    @Override
    public int searchById(Scanner scanner)//Trả về index của phần tử cần tìm
    {
        System.out.println("Danh sách các sản phẩm hiện có");
        showCurrentList();
        System.out.println("Mời lựa chọn sản phẩm bằng cách nhập mã sản phẩm");
        String updateId = scanner.nextLine();
        for (int i = 0; i < productList.length; i++)
        {
            if (productList[i] == null)//Tránh duyệt phần tử null
                return -1;
            if (productList[i].getProductId().equals(updateId))
            {
                return i;
            }
        }
        return -1;
    }

}
