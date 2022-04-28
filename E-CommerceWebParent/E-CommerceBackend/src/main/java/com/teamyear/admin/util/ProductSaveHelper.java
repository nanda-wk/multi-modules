package com.teamyear.admin.util;

import com.teamyear.common.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductSaveHelper {

    public static void setProductImages(List<MultipartFile> files, Product product) throws IOException {
        String uploadDir = "./images/ProductImg/" + product.getId() + "/";
        String dbDir = "/images/ProductImg/" + product.getId() + "/";
        List<String> imgNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "");
            imgNames.add(fileName);
            if (!fileName.isEmpty()) {
                FileUpload.deleteImage(product.getImg1());
                FileUpload.deleteImage(product.getImg2());
                FileUpload.deleteImage(product.getImg3());
                FileUpload.deleteImage(product.getImg4());

                FileUpload.saveImage(file, fileName, uploadDir);
            }
        }
        if (files.size() != 0) {
            product.setImg1(dbDir + imgNames.get(0));
            product.setImg2(dbDir + imgNames.get(1));
            product.setImg3(dbDir + imgNames.get(2));
            product.setImg4(dbDir + imgNames.get(3));
        }

    }

    public static void setProductDetails(Integer[] detailIds, String[] detailNames, String[] detailValues,
                                         Product product) {
        for (int i = 0; i < detailNames.length; i++) {
            Integer id = detailIds[i];
            String name = detailNames[i];
            String value = detailValues[i];
            if (id != 0) {
                product.addDetails(id, name, value);
            } else if (!name.isEmpty() && !value.isEmpty()) {
                product.addDetails(name, value);
            }
        }
    }

}