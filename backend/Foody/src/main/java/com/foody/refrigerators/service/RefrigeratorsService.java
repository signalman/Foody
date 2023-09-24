package com.foody.refrigerators.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.foody.global.exception.ErrorCode;
import com.foody.member.entity.Member;
import com.foody.member.service.MemberService;
import com.foody.refrigerators.dto.request.CustomIngredientRequest;
import com.foody.refrigerators.dto.response.*;
import com.foody.refrigerators.entity.*;
import com.foody.refrigerators.exception.IngredientException;
import com.foody.refrigerators.repository.IngredientCategoryRepository;
import com.foody.refrigerators.repository.IngredientRepository;
import com.foody.refrigerators.repository.RefrigeratorIngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefrigeratorsService {

    private final IngredientRepository ingredientRepository;
    private final RefrigeratorIngredientRepository refrigeratorIngredientRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final MemberService memberService;
    private final WebClient webClient;

    public IngredientCategory findIngredientCategory(Long ingredientCategoryId) {
        return ingredientCategoryRepository.findById(ingredientCategoryId)
                .orElseThrow(
                        () -> new IngredientException(ErrorCode.INGREDIENT_CATEGORY_NOT_FOUND));

    }

    public Ingredient findIngredient(Long ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(
                        () -> new IngredientException(ErrorCode.INGREDIENT_NOT_FOUND));
    }

    public RefrigeratorIngredient findRefrigeratorIngredient(Member member, Ingredient ingredient) {
        return refrigeratorIngredientRepository.findByMemberAndIngredient(member, ingredient)
                .orElseThrow(
                        () -> new IngredientException(ErrorCode.USER_INGREDIENT_NOT_FOUND));
    }

    public boolean existsRefrigeratorIngredient(Member member, Ingredient ingredient) {
        return refrigeratorIngredientRepository.existsByMemberAndIngredient(member, ingredient);
    }

    public Optional<Ingredient> findCustomIngredient(CustomIngredientRequest ingredient) {
        return ingredientRepository.findIngredientByIngredientNameAndIngredientCategory_Id(ingredient.ingredientName(), ingredient.ingredientCategoryId());
    }

    public Ingredient saveCustomIngredient(CustomIngredientRequest customIngredient) {
        IngredientCategory ingredientCategory = findIngredientCategory(customIngredient.ingredientCategoryId());
        return ingredientRepository.save(Ingredient.from(customIngredient.ingredientName(), ingredientCategory));
    }

    public List<SearchIngredientResponse> searchIngredientList(String keyword) {
        return ingredientRepository.findIngredientsByIngredientNameContainingAndIngredientType(keyword, IngredientType.INITIAL)
                .stream().map(SearchIngredientResponse::new).toList();
    }

    public Ingredient searchIngredient(String keyword) {
        return ingredientRepository.findIngredientByIngredientNameAndIngredientType(keyword, IngredientType.INITIAL)
                .orElseThrow(
                        () -> new IngredientException(ErrorCode.INGREDIENT_NOT_FOUND)
                );
    }

    public boolean existsIngredient(String keyword) {
        return ingredientRepository.existsByIngredientNameAndIngredientType(keyword, IngredientType.INITIAL);
    }

    public void insertIngredient(String email,List<Long> ingredients) {

        List<RefrigeratorIngredient> insertIngredients = new ArrayList<>();

        for(long ingredientId : ingredients) {
            Member member = memberService.findByEmail(email);
            Ingredient ingredient = findIngredient(ingredientId);

            RefrigeratorIngredient refrigeratorIngredient =
                    RefrigeratorIngredient.from(member, ingredient);

            if(existsRefrigeratorIngredient(member, ingredient)) {
                insertIngredients.add(refrigeratorIngredient);
            }
        }

        refrigeratorIngredientRepository.saveAll(insertIngredients);
    }

    public void insertCustomIngredient(String email, List<CustomIngredientRequest> customIngredients){

        List<RefrigeratorIngredient> insertIngredients = new ArrayList<>();

        List<Ingredient> ingredients = customIngredients.stream().map(customIngredient -> findCustomIngredient(customIngredient)
                .orElseGet(() -> saveCustomIngredient(customIngredient))).toList();

        for(Ingredient ingredient : ingredients) {
            Member member = memberService.findByEmail(email);

            RefrigeratorIngredient refrigeratorIngredient =
                    RefrigeratorIngredient.from(member, ingredient);

            if(existsRefrigeratorIngredient(member, ingredient)) {
                insertIngredients.add(refrigeratorIngredient);
            }
        }

        refrigeratorIngredientRepository.saveAll(insertIngredients);

    }

    public List<UserRefrigeratorResponse> getUserRefrigerator(String email) {
        Member member = memberService.findByEmail(email);
        List<RefrigeratorIngredient> ingredients = refrigeratorIngredientRepository.findAllByMember(member);
        return ingredients.stream().map(ingredient -> new UserRefrigeratorResponse(ingredient)).toList();
    }

    public void resetRefrigerator(String email) {
        Member member = memberService.findByEmail(email);

        List<RefrigeratorIngredient> ingredients = refrigeratorIngredientRepository.findAllByMember(member);
        refrigeratorIngredientRepository.deleteAllInBatch(ingredients);

    }

    public void deleteUserIngredient(String email, Long ingredientId) {
        Member member = memberService.findByEmail(email);
        Ingredient ingredient = findIngredient(ingredientId);

        RefrigeratorIngredient refrigeratorIngredient = findRefrigeratorIngredient(member, ingredient);

        refrigeratorIngredientRepository.delete(refrigeratorIngredient);
    }

    public List<SearchIngredientResponse> getReceiptIngredient(String imgData) {

        Map<String, Object> receiptData = WebClient.builder()
                .baseUrl("https://h06yrkfqdl.apigw.ntruss.com/custom/v1/24865/a3c7f37381726a3c8db098b0146f830eeabd9de71d2b8322576ce264d9730c8f/document/receipt")
                .defaultHeader("X-OCR-SECRET", "UHFNRFpNZ0NHVFhDc1RrUFVVaEtCVERDdlBPTFJaWFM=")
                .defaultHeader("Content-Type", "application/json")
                .build()
                .post()
                .body(BodyInserters.fromValue(imgData))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        log.debug("영수증 데이터 : {}", receiptData);

//        String json = "{\n" +
//                "    \"version\": \"V2\",\n" +
//                "    \"requestId\": \"795cf393-1b9f-45b2-b3bf-8960bfa390bb\",\n" +
//                "    \"timestamp\": 1694138545687,\n" +
//                "    \"images\": [\n" +
//                "        {\n" +
//                "            \"receipt\": {\n" +
//                "                \"meta\": {\n" +
//                "                    \"estimatedLanguage\": \"ko\"\n" +
//                "                },\n" +
//                "                \"result\": {\n" +
//                "                    \"subResults\": [\n" +
//                "                        {\n" +
//                "                            \"items\": [\n" +
//                "                                 {\n" +
//                "                                    \"name\": {\n" +
//                "                                        \"text\": \"딸기\",\n" +
//                "                                        \"formatted\": {\n" +
//                "                                            \"value\": \"딸기\"\n" +
//                "                                        },\n" +
//                "                                        \"boundingPolys\": [\n" +
//                "                                            {\n" +
//                "                                                \"vertices\": [\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 289.0,\n" +
//                "                                                        \"y\": 49.0\n" +
//                "                                                    },\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 328.0,\n" +
//                "                                                        \"y\": 49.0\n" +
//                "                                                    },\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 328.0,\n" +
//                "                                                        \"y\": 72.0\n" +
//                "                                                    },\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 289.0,\n" +
//                "                                                        \"y\": 72.0\n" +
//                "                                                    }\n" +
//                "                                                ]\n" +
//                "                                            }\n" +
//                "                                        ],\n" +
//                "                                        \"maskingPolys\": []\n" +
//                "                                    },\n" +
//                "                                    \"count\": {\n" +
//                "                                        \"text\": \"1\",\n" +
//                "                                        \"formatted\": {\n" +
//                "                                            \"value\": \"1\"\n" +
//                "                                        },\n" +
//                "                                        \"boundingPolys\": [\n" +
//                "                                            {\n" +
//                "                                                \"vertices\": [\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 517.0,\n" +
//                "                                                        \"y\": 66.0\n" +
//                "                                                    },\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 529.0,\n" +
//                "                                                        \"y\": 66.0\n" +
//                "                                                    },\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 529.0,\n" +
//                "                                                        \"y\": 80.0\n" +
//                "                                                    },\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 517.0,\n" +
//                "                                                        \"y\": 80.0\n" +
//                "                                                    }\n" +
//                "                                                ]\n" +
//                "                                            }\n" +
//                "                                        ]\n" +
//                "                                    },\n" +
//                "                                    \"price\": {\n" +
//                "                                        \"price\": {\n" +
//                "                                            \"text\": \"10,000\",\n" +
//                "                                            \"formatted\": {\n" +
//                "                                                \"value\": \"10000\"\n" +
//                "                                            },\n" +
//                "                                            \"boundingPolys\": [\n" +
//                "                                                {\n" +
//                "                                                    \"vertices\": [\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 552.0,\n" +
//                "                                                            \"y\": 59.0\n" +
//                "                                                        },\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 613.0,\n" +
//                "                                                            \"y\": 54.0\n" +
//                "                                                        },\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 615.0,\n" +
//                "                                                            \"y\": 73.0\n" +
//                "                                                        },\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 554.0,\n" +
//                "                                                            \"y\": 78.0\n" +
//                "                                                        }\n" +
//                "                                                    ]\n" +
//                "                                                }\n" +
//                "                                            ]\n" +
//                "                                        },\n" +
//                "                                        \"unitPrice\": {\n" +
//                "                                            \"text\": \"10,000\",\n" +
//                "                                            \"formatted\": {\n" +
//                "                                                \"value\": \"10000\"\n" +
//                "                                            },\n" +
//                "                                            \"boundingPolys\": [\n" +
//                "                                                {\n" +
//                "                                                    \"vertices\": [\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 436.0,\n" +
//                "                                                            \"y\": 73.0\n" +
//                "                                                        },\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 494.0,\n" +
//                "                                                            \"y\": 68.0\n" +
//                "                                                        },\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 495.0,\n" +
//                "                                                            \"y\": 87.0\n" +
//                "                                                        },\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 438.0,\n" +
//                "                                                            \"y\": 92.0\n" +
//                "                                                        }\n" +
//                "                                                    ]\n" +
//                "                                                }\n" +
//                "                                            ]\n" +
//                "                                        }\n" +
//                "                                    }\n" +
//                "                                },\n" +
//                "\t\t\t\t\t\t\t{\n" +
//                "                                    \"name\": {\n" +
//                "                                        \"text\": \"대림순두부400g\",\n" +
//                "                                        \"formatted\": {\n" +
//                "                                            \"value\": \"대림순두부400g\"\n" +
//                "                                        },\n" +
//                "                                        \"boundingPolys\": [\n" +
//                "                                            {\n" +
//                "                                                \"vertices\": [\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 269.0,\n" +
//                "                                                        \"y\": 486.0\n" +
//                "                                                    },\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 409.0,\n" +
//                "                                                        \"y\": 486.0\n" +
//                "                                                    },\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 409.0,\n" +
//                "                                                        \"y\": 511.0\n" +
//                "                                                    },\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 269.0,\n" +
//                "                                                        \"y\": 511.0\n" +
//                "                                                    }\n" +
//                "                                                ]\n" +
//                "                                            }\n" +
//                "                                        ],\n" +
//                "                                        \"maskingPolys\": []\n" +
//                "                                    },\n" +
//                "                                    \"count\": {\n" +
//                "                                        \"text\": \"1\",\n" +
//                "                                        \"formatted\": {\n" +
//                "                                            \"value\": \"1\"\n" +
//                "                                        },\n" +
//                "                                        \"boundingPolys\": [\n" +
//                "                                            {\n" +
//                "                                                \"vertices\": [\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 521.0,\n" +
//                "                                                        \"y\": 513.0\n" +
//                "                                                    },\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 535.0,\n" +
//                "                                                        \"y\": 513.0\n" +
//                "                                                    },\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 535.0,\n" +
//                "                                                        \"y\": 533.0\n" +
//                "                                                    },\n" +
//                "                                                    {\n" +
//                "                                                        \"x\": 521.0,\n" +
//                "                                                        \"y\": 533.0\n" +
//                "                                                    }\n" +
//                "                                                ]\n" +
//                "                                            }\n" +
//                "                                        ]\n" +
//                "                                    },\n" +
//                "                                    \"price\": {\n" +
//                "                                        \"price\": {\n" +
//                "                                            \"text\": \"500\",\n" +
//                "                                            \"formatted\": {\n" +
//                "                                                \"value\": \"500\"\n" +
//                "                                            },\n" +
//                "                                            \"boundingPolys\": [\n" +
//                "                                                {\n" +
//                "                                                    \"vertices\": [\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 590.0,\n" +
//                "                                                            \"y\": 511.0\n" +
//                "                                                        },\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 629.0,\n" +
//                "                                                            \"y\": 511.0\n" +
//                "                                                        },\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 629.0,\n" +
//                "                                                            \"y\": 535.0\n" +
//                "                                                        },\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 590.0,\n" +
//                "                                                            \"y\": 535.0\n" +
//                "                                                        }\n" +
//                "                                                    ]\n" +
//                "                                                }\n" +
//                "                                            ]\n" +
//                "                                        },\n" +
//                "                                        \"unitPrice\": {\n" +
//                "                                            \"text\": \"500\",\n" +
//                "                                            \"formatted\": {\n" +
//                "                                                \"value\": \"500\"\n" +
//                "                                            },\n" +
//                "                                            \"boundingPolys\": [\n" +
//                "                                                {\n" +
//                "                                                    \"vertices\": [\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 460.0,\n" +
//                "                                                            \"y\": 511.0\n" +
//                "                                                        },\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 497.0,\n" +
//                "                                                            \"y\": 511.0\n" +
//                "                                                        },\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 497.0,\n" +
//                "                                                            \"y\": 534.0\n" +
//                "                                                        },\n" +
//                "                                                        {\n" +
//                "                                                            \"x\": 460.0,\n" +
//                "                                                            \"y\": 534.0\n" +
//                "                                                        }\n" +
//                "                                                    ]\n" +
//                "                                                }\n" +
//                "                                            ]\n" +
//                "                                        }\n" +
//                "                                    }\n" +
//                "                                }\n" +
//                "                            ]\n" +
//                "                        }\n" +
//                "                    ],\n" +
//                "                    \"totalPrice\": {\n" +
//                "                        \"price\": {\n" +
//                "                            \"text\": \"33,260\",\n" +
//                "                            \"formatted\": {\n" +
//                "                                \"value\": \"33260\"\n" +
//                "                            },\n" +
//                "                            \"boundingPolys\": [\n" +
//                "                                {\n" +
//                "                                    \"vertices\": [\n" +
//                "                                        {\n" +
//                "                                            \"x\": 506.0,\n" +
//                "                                            \"y\": 771.0\n" +
//                "                                        },\n" +
//                "                                        {\n" +
//                "                                            \"x\": 643.0,\n" +
//                "                                            \"y\": 773.0\n" +
//                "                                        },\n" +
//                "                                        {\n" +
//                "                                            \"x\": 643.0,\n" +
//                "                                            \"y\": 800.0\n" +
//                "                                        },\n" +
//                "                                        {\n" +
//                "                                            \"x\": 505.0,\n" +
//                "                                            \"y\": 798.0\n" +
//                "                                        }\n" +
//                "                                    ]\n" +
//                "                                }\n" +
//                "                            ]\n" +
//                "                        }\n" +
//                "                    }\n" +
//                "                }\n" +
//                "            },\n" +
//                "            \"uid\": \"fddf79594ca9422a942cd63b9a8dba4e\",\n" +
//                "            \"name\": \"demo\",\n" +
//                "            \"inferResult\": \"SUCCESS\",\n" +
//                "            \"message\": \"SUCCESS\",\n" +
//                "            \"validationResult\": {\n" +
//                "                \"result\": \"NO_REQUESTED\"\n" +
//                "            }\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        Map<String, Object> receiptData = new HashMap<>();
//        try {
//            receiptData = objectMapper.readValue(json, Map.class);
//        } catch (Exception e) {
//
//        }

        Map<String, Object> receipt = (Map<String, Object>) ((List<Map>) receiptData.get("images")).get(0).get("receipt");
        List<Map> items = (List<Map>) ((List<Map>) ((Map<String, Object>) receipt.get("result")).get("subResults")).get(0).get("items");

        List<SearchIngredientResponse> list = new ArrayList<>();

        for(int i = 0; i < items.size(); i++) {
            Map<String, Object> name = (Map<String, Object>) ((Map<String, Object>) items.get(i)).get("name");
            String itemName = (String) ((Map<String, Object>) name.get("formatted")).get("value");

            log.debug("상품명 : {}", itemName);

            Map<String, Object> itemCategorys = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("query", itemName)
                            .queryParam("display","4")
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            Map<String, Object> itemCategory = ((List<Map>) itemCategorys.get("items")).get(3);

            String itemMappingResult = ((String) itemCategory.get("category4")).isBlank()
                    ? ((String) itemCategory.get("category3")) : ((String) itemCategory.get("category4"));

            log.debug("네이버 쇼핑 API 호출 결과 : {}", itemMappingResult);

            log.debug("재료 DB에 존재 여부 : {}", existsIngredient(itemMappingResult));
            if(existsIngredient(itemMappingResult)) {
                list.add(new SearchIngredientResponse(searchIngredient(itemMappingResult)));
            } else {
                list.add(new SearchIngredientResponse(itemName));
            }
        }

        return list;

    }

}
