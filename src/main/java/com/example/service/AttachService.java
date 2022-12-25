package com.example.service;

import com.example.dto.AttachDTO;
import com.example.entity.AttachEntity;
import com.example.exceptions.ItemNotFoundException;
import com.example.repository.AttachRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class AttachService {

    //    attaches/
    @Value("${attach.upload.folder}")
    public String attachFolder;
    @Value("${attach.open.url}")
    public String attachOpenUrl;
    @Autowired
    private AttachRepository attachRepository;


    // 1.Create Attach(upload)
    public AttachDTO saveToSystem(MultipartFile file) {
        try {

            log.info("{Attach save}" + file);
            // attaches/2022/04/23/UUID.png
            String attachPath = getYmDString(); // 2022/04/23
            String extension = getExtension(file.getOriginalFilename()); // .png
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "." + extension; // UUID.png

            File folder = new File(attachFolder + attachPath);  // attaches/2022/12/21/
            if (!folder.exists()) {
                folder.mkdirs();
            }

            byte[] bytes = file.getBytes();


            Path path = Paths.get(attachFolder + attachPath + "/" + fileName); // attaches/2022/04/23/UUID.png
            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setPath(attachPath);
            entity.setExtension(extension);
            entity.setSize(file.getSize());
            entity.setOriginalName(file.getOriginalFilename());
            entity.setCreatedData(LocalDateTime.now());
            entity.setAttachOpenUrl(attachOpenUrl + fileName);
            entity.setId(uuid);
            attachRepository.save(entity);

            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(entity.getId());
            attachDTO.setOriginalName(file.getOriginalFilename());
            attachDTO.setUrl(attachOpenUrl + fileName);

            return attachDTO;
        } catch (IOException e) {
            log.warn("file not saved " + file.getOriginalFilename());
            e.printStackTrace();
        }
        return null;
    }


    public byte[] loadImage(String fileName) {
        byte[] imageInByte;
        BufferedImage originalImage;
        try {
            log.info("{load image}" + fileName);
            originalImage = ImageIO.read(new File(attachFolder + "2022/12/21/" + fileName));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", baos);

            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }


    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/12/21
    }

    // mp3/jpg/npg/mp4
    public String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }


    //2. Get Attach By Id (Open)
    public byte[] openGeneral(String fileName) {
        byte[] data;
        try {
            Path file = Paths.get(attachFolder + fileName);
            data = Files.readAllBytes(file);
            log.info(fileName + "{attach opened}");
            return data;
        } catch (IOException e) {
            log.warn("attach open failed");
            e.printStackTrace();
        }
        return new byte[0];
    }


    // 3. Download Attach (Download)
    public Resource download(String fileName) {
        try {
            Path file = Paths.get(attachFolder + fileName);
            Resource resource = new UrlResource(file.toUri());


            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    // 4. Attach pagination (ADMIN) id,original_name,size,url
    public Page<AttachDTO> getList(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page - 1, size);

        Page<AttachEntity> entityPage = attachRepository.findAll(paging);

        List<AttachEntity> entityList = entityPage.getContent();

        List<AttachDTO> dtoList = new ArrayList<>();

        for (AttachEntity attachEntity : entityList) {
            AttachDTO attachDTO = toDTO(attachEntity);
            dtoList.add(attachDTO);
        }

        return new PageImpl<>(dtoList, paging, entityPage.getTotalElements());
    }

    public AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setOriginalName(entity.getOriginalName());
        dto.setSize(entity.getSize());
        dto.setPath(entity.getPath());
        dto.setCreatedData(entity.getCreatedData());
        return dto;
    }
    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Attach not found"));
    }


}
