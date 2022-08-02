package com.whitecatsama.delicious_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whitecatsama.delicious_take_out.domain.AddressBook;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AddressBookService extends IService<AddressBook> {
}
