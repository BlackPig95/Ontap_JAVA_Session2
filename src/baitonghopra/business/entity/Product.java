package baitonghopra.business.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Product
{
    private String productId;
    private String productName;
    private float price;
    private String description;
    private Date createdDate;
    private int catalogId;
    private int productStatus;

    public Product()
    {
    }

    public Product(String productId, String productName, float price, String description, Date createdDate, int catalogId, int productStatus)
    {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.createdDate = createdDate;
        this.catalogId = catalogId;
        this.productStatus = productStatus;
    }

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public int getCatalogId()
    {
        return catalogId;
    }

    public void setCatalogId(int catalogId)
    {
        this.catalogId = catalogId;
    }

    public int getProductStatus()
    {
        return productStatus;
    }

    public void setProductStatus(int productStatus)
    {
        this.productStatus = productStatus;
    }

    public void displayData()
    {
        //        for(int i = 0; i < categoriesArray.length; i++)
//        {
//            if(this.catalogId == categoriesArray[i].getId)
//                => set catalogId
//        }
        System.out.printf("Mã sản phẩm: %s | Tên sản phẩm: %s | Giá sản phẩm: %.1f | Mô tả sản phẩm: %s" +
                        " | Ngày thêm sản phẩm: %s | Danh mục sản phẩm: %s | Trạng thái sản phẩm: %s\n",
                this.productId, this.productName, this.price, this.description, this.createdDate,
                this.catalogId, this.productStatus == 0 ? "Đang bán" :
                        (this.productStatus == 1 ? "Hết hàng" : "Không bán"));
    }

    public void inputData(Scanner scanner, Product[] productArray, Categories[] categoriesArray, boolean isAdding)
    {
        inputCatalogId(scanner, categoriesArray);
        if (isAdding)//Nếu đang update thì không cho sửa Id
            inputProductId(scanner, productArray);
        inputProductName(scanner, productArray, isAdding);
        while (true)
        {
            System.out.println("Nhập giá sản phẩm");
            this.price = Float.parseFloat(scanner.nextLine());
            if (this.price < 0)
            {
                System.out.println("Giá bán phải lớn hơn 0");
                continue;
            }
            break;
        }
        System.out.println("Nhập mô tả sản phẩm");
        this.description = scanner.nextLine();
        setDate(scanner);
        while (true)
        {
            System.out.println("Nhập vào trạng thái sản phẩm: 0 - Đang bán, 1 - Hết hàng, 2 - Không bán");
            this.productStatus = Integer.parseInt(scanner.nextLine());
            if (this.productStatus == 0 || this.productStatus == 1 || this.productStatus == 2)
                break;
            System.out.println("Lựa chọn không khả dụng, vui lòng nhập lại");
        }
    }

    private void inputProductId(Scanner scanner, Product[] productArray)
    {
        while (true)
        {
            System.out.println("Nhập mã sản phẩm, bắt đầu bằng C/S/A và theo sau là 3 chữ số");
            String inputId = scanner.nextLine();
            if (!checkInputId(inputId))
            {
                System.out.println("Mã sản phẩm không đúng định dạng, vui lòng nhập lại");
                continue;
            }
            if (checkIdDuplicate(inputId, productArray))
            {
                System.out.println("Mã sản phẩm đã tồn tại, vui lòng nhập mã khác");
                continue;
            }
            this.productId = inputId;
            break;
        }
    }

    private boolean checkInputId(String inputId)
    {
        String regex = "^[CSA]\\d{3}$";
        return inputId.matches(regex);
    }

    private boolean checkIdDuplicate(String inputId, Product[] productArray)
    {   //Kiểm tra trùng lặp
        for (int i = 0; i < productArray.length; i++)
        {
            if (productArray[i] == null)//Tránh việc duyệt phần tử null
                return false;//Duyệt đến phần tử null mà chưa tìm thấy => Không bị trùng
            if (productArray[i].productId.equals(inputId))
            {
                return true;//true => đã tồn tại
            }
        }
        return false;
    }

    private void inputProductName(Scanner scanner, Product[] productArray, boolean isAdding)
    {
        while (true)
        {
            System.out.println("Nhập tên sản phẩm từ 10-50 ký tự");
            String inputName = scanner.nextLine();
            if (checkInputName(productArray, inputName, isAdding))//true => Thỏa mãn các yêu câu
            {
                this.productName = inputName;
                break;
            }
        }
    }

    private boolean checkInputName(Product[] productArray, String nameInput, boolean isAdding)
    {
        String regex = "^.{10,50}$";
        if (!nameInput.matches(regex))//Tên không đúng định dạng
        {
            System.out.println("Vui lòng nhập đúng định dạng");
            return false;
        }
        for (int i = 0; i < productArray.length; i++)
        {
            if (productArray[i] == null)//Duyệt hết list => Không bị trùng tên
                return true;
            if (productArray[i].productName.equals(nameInput))
            {
                if (isAdding)//Nếu đang update thì cho phép nhập trùng tên cũ
                {
                    System.out.println("Tên đã tồn tại");
                    return false; //=>Đã tồn tại
                }
            }
        }
        return true;
    }

    private void setDate(Scanner scanner)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        while (true)
        {
            boolean dateGood;
            System.out.println("Nhập ngày thêm sản phẩm này: (dd/MM/yyyy)");
            try
            {
                createdDate = simpleDateFormat.parse(scanner.nextLine());
                dateGood = true;
            } catch (ParseException e)
            {
                System.out.println("Vui lòng nhập đúng định dạng");
                dateGood = false;
            }
            if (dateGood)
                break;
        }
    }

    private void inputCatalogId(Scanner scanner, Categories[] categoriesArray)
    {
        System.out.println("Danh sách các danh mục hiện có");
        for (Categories category : categoriesArray)
        {
            if (category == null)
                break;
            category.displayData();
        }
        outer:
        while (true)
        {
            System.out.println("Mời nhập mã Id danh mục muốn chọn");
            int catalogIdInput = Integer.parseInt(scanner.nextLine());
            for (Categories category : categoriesArray)
            {
                if (category == null)
                    break;
                if (category.getCatalogId() == catalogIdInput)
                {
                    this.catalogId = catalogIdInput;
                    break outer;//Break vòng while
                }
            }
            //Chạy hết vòng for mà không tìm thấy => Nhập sai
            System.out.println("Mã không đúng vui lòng nhập lại");
        }
    }
}
