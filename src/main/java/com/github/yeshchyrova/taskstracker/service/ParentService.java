package com.github.yeshchyrova.taskstracker.service;

import com.github.yeshchyrova.taskstracker.model.Parent;
import com.github.yeshchyrova.taskstracker.repository.ParentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.github.yeshchyrova.taskstracker.constant.Constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ParentService {
  private final ParentRepository parentRepository;

  public Page<Parent> getAllParents(int page, int size) {
    return parentRepository.findAll(PageRequest.of(page, size));
  }

  public Parent getParentById(String id) {
    return parentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Parent not found"));
  }

  public Parent createParent(Parent parent) {
    return parentRepository.save(parent);
  }

  public void deleteParent(String id) {
    Parent parent = parentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Parent not found"));
    parentRepository.delete(parent);
  }

//  ? --------- these 2 methods will be used for task entity, not for parent ----------

//  public String uploadReport(String id, MultipartFile file) {
//    Parent parent = getParentById(id);
//    String reportUrl = photoFunction.apply(id, file);
//    parent.setReport(reportUrl); // for column with name "report"
//    parentRepository.save(parent);
//    return reportUrl;
//  }
//
//  private final Function<String, String> fileExtension =
//          (String fileName) -> Optional.of(fileName).filter(name -> name.contains("."))
//                  .map(name -> "." + name.substring(name.lastIndexOf(".") + 1)).orElse(".png");

//  private final BiFunction<String, MultipartFile, String> photoFunction = (id, file) -> {
//    String filename = id + fileExtension.apply(file.getOriginalFilename());
//    try {
//      Path fileStorage = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
//      if (!Files.exists(fileStorage)) {
//        Files.createDirectories(fileStorage);
//      }
//      Files.copy(file.getInputStream(),
//                 fileStorage.resolve(filename),
//                 REPLACE_EXISTING);
//
//      return ServletUriComponentsBuilder.fromCurrentContextPath()
//              .path("/tasks/image/" + filename).toUriString();
//      // прописать путь (path), по которому будет доступ к изображению
////      here is the example: https://youtu.be/-LUA-LHXobE?si=-rvFSOQzjLFsNEGh&t=1813
//    } catch (Exception e) {
//      throw new RuntimeException("Unable to save image");
//    }
//  };

}
