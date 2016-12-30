package com.beamofsoul.springboot.management.util;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.NonNull;

public class PageUtil {

	public static final String PAGEABLE_PAGE_NUMBER_NAME = "page";
	public static final String PAGEABLE_PAGE_SIZE_NAME = "size";

	public static int getFirstResult(int pageNumber, int pageSize) {
		return pageNumber * pageSize;
	}

	public static int getFirstResult(@NonNull Pageable pageable) {
		return getFirstResult(pageable.getPageNumber(), pageable.getPageSize());
	}

	public static int getMaxResult(int pageNumber, int pageSize) {
		// return (pageNumber + 1) * pageSize - 1;
		return (pageNumber + 1) * pageSize;
	}

	public static int getMaxResult(@NonNull Pageable pageable) {
		return getMaxResult(pageable.getPageNumber(), pageable.getPageSize());
	}

	public static int getTotalPages(int pageSize, long recordsCount) {
		return (int) Math.ceil((float) recordsCount / (pageSize > 0 ? pageSize : 1));
	}

	public static int getTotalPages(@NonNull Pageable pageable, long recordsCount) {
		return getTotalPages(pageable.getPageSize(), recordsCount);
	}

	public static Pageable parsePageable(Map<String, Integer> map) {
		return parsePageable(map, Constants.DEFAULT_ENTITY_PRIMARY_KEY);
	}

	public static Pageable parsePageable(Map<String, Integer> map, String primaryKey) {
		return parsePageable(map, new Sort(Direction.ASC, primaryKey));
	}

	public static Pageable parsePageable(Map<String, Integer> map, Direction direction) {
		return parsePageable(map, new Sort(direction, Constants.DEFAULT_ENTITY_PRIMARY_KEY));
	}

	public static Pageable parsePageable(Map<String, Integer> map, Direction direction, String primaryKey) {
		return parsePageable(map, new Sort(direction, primaryKey));
	}

	public static Pageable parsePageable(Map<String, Integer> map, Sort sort) {
		return parsePageable(map.get(PAGEABLE_PAGE_NUMBER_NAME), map.get(PAGEABLE_PAGE_SIZE_NAME), sort);
	}

	public static Pageable parsePageable(int pageNumber, int pageSize, String primaryKey) {
		return parsePageable(pageNumber, pageSize, new Sort(Direction.ASC, primaryKey));
	}

	public static Pageable parsePageable(int pageNumber, int pageSize, Direction direction) {
		return parsePageable(pageNumber, pageSize, new Sort(direction, Constants.DEFAULT_ENTITY_PRIMARY_KEY));
	}
	
	public static Pageable parsePageable(int pageNumber, int pageSize, Direction direction, String primaryKey) {
		return parsePageable(pageNumber, pageSize, new Sort(direction, primaryKey));
	}

	public static Pageable parsePageable(int pageNumber, int pageSize, Sort sort) {
		return new PageRequest(pageNumber, pageSize, sort);
	}
}
