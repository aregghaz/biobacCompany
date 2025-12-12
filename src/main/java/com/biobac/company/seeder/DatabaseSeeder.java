//package com.biobac.company.seeder;
//
//import com.biobac.company.entity.*;
//import com.biobac.company.repository.*;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class DatabaseSeeder implements CommandLineRunner {
//
//    private final CompanyTypeRepository companyTypeRepository;
//    private final RegionRepository regionRepository;
//    private final SaleTypeRepository saleTypeRepository;
//    private final ContractFormRepository contractFormRepository;
//    private final FinancialTermsRepository financialTermsRepository;
//    private final DeliveryPayerRepository deliveryPayerRepository;
//    private final DeliveryMethodRepository deliveryMethodRepository;
//    private final ClientTypeRepository clientTypeRepository;
//    private final CooperationRepository cooperationRepository;
//    private final LineRepository lineRepository;
//    private final SaleStatusRepository saleStatusRepository;
//
//    public DatabaseSeeder(CompanyTypeRepository companyTypeRepository, RegionRepository regionRepository, SaleTypeRepository saleTypeRepository, ContractFormRepository contractFormRepository, FinancialTermsRepository financialTermsRepository, DeliveryPayerRepository deliveryPayerRepository, DeliveryMethodRepository deliveryMethodRepository, ClientTypeRepository clientTypeRepository, CooperationRepository cooperationRepository, LineRepository lineRepository, SaleStatusRepository saleStatusRepository) {
//        this.companyTypeRepository = companyTypeRepository;
//        this.regionRepository = regionRepository;
//        this.saleTypeRepository = saleTypeRepository;
//        this.contractFormRepository = contractFormRepository;
//        this.financialTermsRepository = financialTermsRepository;
//        this.deliveryPayerRepository = deliveryPayerRepository;
//        this.deliveryMethodRepository = deliveryMethodRepository;
//        this.clientTypeRepository = clientTypeRepository;
//        this.cooperationRepository = cooperationRepository;
//        this.lineRepository = lineRepository;
//        this.saleStatusRepository = saleStatusRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (saleStatusRepository.count() == 0) {
//            List<String> statuses = List.of(
//                    "завершенные",
//                    "не оплачено",
//                    "незавершенные"
//            );
//
//            statuses.forEach(s -> saleStatusRepository.save(new SaleStatus(s)));
//        }
//
//        if (companyTypeRepository.count() == 0) {
//            CompanyType companyType1 = new CompanyType("Покупатели");
//            companyTypeRepository.save(companyType1);
//
//            CompanyType companyType2 = new CompanyType("Поставщики");
//            companyTypeRepository.save(companyType2);
//        }
//
//        if (lineRepository.count() == 0) {
//            List<String> lines = List.of(
//                    "Gurgen Line",
//                    "Анапа",
//                    "Армавир",
//                    "Бетонка",
//                    "Быковское ш",
//                    "Владимир-Иваново",
//                    "Волоколамское ш",
//                    "Горьковское ш",
//                    "Дмитровское ш",
//                    "Дубна",
//                    "Егорьевское ш",
//                    "Закрытые",
//                    "Калуга",
//                    "Калужского ш",
//                    "Каширское ш",
//                    "Киевское ш",
//                    "Ключевые клиенты",
//                    "Краснодар",
//                    "Ленинградское ш",
//                    "Майкоп",
//                    "Маркетплейс",
//                    "Можайское ш",
//                    "Нижний Новгород",
//                    "Новорыжское ш",
//                    "Новорязанское ш",
//                    "Ногинск",
//                    "Носовыхинское ш",
//                    "Оптовая продажа",
//                    "Подольск, Чехов",
//                    "Поставщики",
//                    "Пятигорск",
//                    "Пятницкое ш",
//                    "Рогачовское ш",
//                    "Ростов",
//                    "Руза",
//                    "Рязань",
//                    "Симферопольское ш",
//                    "Сочи-Адлер",
//                    "Ставрополь",
//                    "Тверь",
//                    "Тихорецк",
//                    "Тула",
//                    "Щелковское ш",
//                    "Ярославль-Кострома",
//                    "Ярославское ш"
//            );
//
//            lines.forEach(name -> lineRepository.save(new Line(name, null)));
//        }
//
//        if (contractFormRepository.count() == 0) {
//            List<String> forms = List.of(
//                    "Наша типовая форма",
//                    "Форма клиента",
//                    "Наша форма с Протоколом разногласий",
//                    "Форма клиента с Протоколом разногласий",
//                    "Наша типовая форма СТМ"
//            );
//
//            forms.forEach(f -> contractFormRepository.save(new ContractForm(f)));
//        }
//
//        if (deliveryMethodRepository.count() == 0) {
//            List<String> methods = List.of(
//                    "Самовывоз",
//                    "До терминала ТК, МО",
//                    "ДО РЦ или склад МО",
//                    "До терминала ТК РФ",
//                    "До адреса РФ",
//                    "СДЭК ИМ",
//                    "Почта РФ ИМ",
//                    "Яндекс доставка ИМ",
//                    "Курьер ИМ",
//                    "До терминала РБ"
//            );
//
//            methods.forEach(m -> deliveryMethodRepository.save(new DeliveryMethod(m)));
//        }
//
//        if (financialTermsRepository.count() == 0) {
//            List<String> terms = List.of(
//                    "Отсрочка 30",
//                    "Отсрочка 60",
//                    "Отсрочка 80",
//                    "Отсрочка 90",
//                    "Реализация",
//                    "Предоплата 50%",
//                    "Предоплата",
//                    "Другие"
//            );
//
//            terms.forEach(t -> financialTermsRepository.save(new FinancialTerms(t)));
//        }
//
//        if (deliveryPayerRepository.count() == 0) {
//            List<String> payers = List.of(
//                    "Поставщик",
//                    "Клиент",
//                    "Биобак"
//            );
//
//            payers.forEach(p -> deliveryPayerRepository.save(new DeliveryPayer(p)));
//        }
//
//        if (cooperationRepository.count() == 0) {
//
//            List<String> statuses = List.of(
//                    "Да",
//                    "Нет",
//                    "Закрыт",
//                    "Должник",
//                    "Реанимация"
//            );
//            statuses.forEach(s -> cooperationRepository.save(new Cooperation(s)));
//        }
//
//        if (clientTypeRepository.count() == 0) {
//
//            List<String> clientTypes = List.of(
//                    "Поставщик",
//                    "Локальная сеть",
//                    "Федеральная сеть",
//                    "Розница хозяйственный",
//                    "Маркетплейс",
//                    "Розница садовод",
//                    "Розница сантехника",
//                    "Интернет магазин",
//                    "Бассейн",
//                    "Агрофирма",
//                    "СТМ",
//                    "Частное лицо",
//                    "Дистрибьютор",
//                    "Селлер",
//                    "Опт база",
//                    "Другое"
//            );
//
//            clientTypes.forEach(type -> clientTypeRepository.save(new ClientType(type)));
//        }
//
//        if (saleTypeRepository.count() == 0) {
//            SaleType saleType1 = new SaleType("Оптовая торговля");
//            saleTypeRepository.save(saleType1);
//
//            SaleType saleType2 = new SaleType("Розничная торговля");
//            saleTypeRepository.save(saleType2);
//        }
//
//        if (regionRepository.count() == 0) {
//            List<Region> regions = Arrays.asList(
//                    new Region("Республика Адыгея", "ADY"),
//                    new Region("Республика Алтай", "ALT"),
//                    new Region("Алтайский край", "ALC"),
//                    new Region("Амурская область", "AMU"),
//                    new Region("Архангельская область", "ARK"),
//                    new Region("Астраханская область", "AST"),
//                    new Region("Белгородская область", "BEL"),
//                    new Region("Брянская область", "BRY"),
//                    new Region("Республика Бурятия", "BUR"),
//                    new Region("Чеченская Республика", "CHC"),
//                    new Region("Челябинская область", "CHL"),
//                    new Region("Чукотский автономный округ", "CHU"),
//                    new Region("Чувашская Республика", "CHV"),
//                    new Region("Республика Дагестан", "DAG"),
//                    new Region("Республика Ингушетия", "ING"),
//                    new Region("Иркутская область", "IRK"),
//                    new Region("Ивановская область", "IVA"),
//                    new Region("Еврейская автономная область", "JAO"),
//                    new Region("Кабардино-Балкарская Республика", "KBR"),
//                    new Region("Калининградская область", "KGD"),
//                    new Region("Калужская область", "KLU"),
//                    new Region("Камчатский край", "KAM"),
//                    new Region("Карачаево-Черкесская Республика", "KCR"),
//                    new Region("Кемеровская область", "KEM"),
//                    new Region("Кировская область", "KIR"),
//                    new Region("Республика Коми", "KOM"),
//                    new Region("Костромская область", "KOS"),
//                    new Region("Краснодарский край", "KDA"),
//                    new Region("Красноярский край", "KYA"),
//                    new Region("Курганская область", "KGN"),
//                    new Region("Курская область", "KRS"),
//                    new Region("Ленинградская область", "LEN"),
//                    new Region("Липецкая область", "LIP"),
//                    new Region("Магаданская область", "MAG"),
//                    new Region("Республика Марий Эл", "MAE"),
//                    new Region("Республика Мордовия", "MOR"),
//                    new Region("Москва", "MSC"),
//                    new Region("Московская область", "MSO"),
//                    new Region("Мурманская область", "MUR"),
//                    new Region("Ненецкий автономный округ", "NAO"),
//                    new Region("Нижегородская область", "NIZ"),
//                    new Region("Новгородская область", "NOV"),
//                    new Region("Новосибирская область", "NVS"),
//                    new Region("Омская область", "OMS"),
//                    new Region("Оренбургская область", "ORE"),
//                    new Region("Орловская область", "ORL"),
//                    new Region("Пензенская область", "PNZ"),
//                    new Region("Пермский край", "PER"),
//                    new Region("Приморский край", "PRI"),
//                    new Region("Псковская область", "PSK"),
//                    new Region("Республика Башкортостан", "BAS"),
//                    new Region("Республика Калмыкия", "KAL"),
//                    new Region("Республика Карелия", "KAR"),
//                    new Region("Республика Хакасия", "HAK"),
//                    new Region("Республика Саха (Якутия)", "SAK"),
//                    new Region("Республика Татарстан", "TAT"),
//                    new Region("Республика Тыва", "TYV"),
//                    new Region("Удмуртская Республика", "UDM"),
//                    new Region("Ростовская область", "ROS"),
//                    new Region("Рязанская область", "RYZ"),
//                    new Region("Санкт-Петербург", "SPB"),
//                    new Region("Сахалинская область", "SAH"),
//                    new Region("Самарская область", "SAM"),
//                    new Region("Саратовская область", "SAR"),
//                    new Region("Смоленская область", "SMO"),
//                    new Region("Ставропольский край", "STA"),
//                    new Region("Свердловская область", "SVE"),
//                    new Region("Тамбовская область", "TAM"),
//                    new Region("Тверская область", "TVE"),
//                    new Region("Томская область", "TOM"),
//                    new Region("Тульская область", "TUL"),
//                    new Region("Тюменская область", "TYU"),
//                    new Region("Ульяновская область", "ULY"),
//                    new Region("Владимирская область", "VLA"),
//                    new Region("Волгоградская область", "VGG"),
//                    new Region("Вологодская область", "VLG"),
//                    new Region("Воронежская область", "VOR"),
//                    new Region("Ямало-Ненецкий автономный округ", "YNA"),
//                    new Region("Ярославская область", "YAR"),
//                    new Region("Донецкая Народная Республика", "DNR"),
//                    new Region("Луганская Народная Республика", "LNR"),
//                    new Region("Запорожская область", "ZAP"),
//                    new Region("Херсонская область", "KHE")
//            );
//            regionRepository.saveAll(regions);
//        }
//
//    }
//}