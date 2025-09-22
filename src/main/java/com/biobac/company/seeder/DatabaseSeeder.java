package com.biobac.company.seeder;

import com.biobac.company.entity.CompanyType;
import com.biobac.company.entity.Region;
import com.biobac.company.entity.SaleType;
import com.biobac.company.repository.CompanyTypeRepository;
import com.biobac.company.repository.RegionRepository;
import com.biobac.company.repository.SaleTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CompanyTypeRepository companyTypeRepository;
    private final RegionRepository regionRepository;
    private final SaleTypeRepository saleTypeRepository;

    public DatabaseSeeder(CompanyTypeRepository companyTypeRepository, RegionRepository regionRepository, SaleTypeRepository saleTypeRepository) {
        this.companyTypeRepository = companyTypeRepository;
        this.regionRepository = regionRepository;
        this.saleTypeRepository = saleTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (companyTypeRepository.count() == 0) {
            CompanyType companyType1 = new CompanyType("buyer");
            companyTypeRepository.save(companyType1);

            CompanyType companyType2 = new CompanyType("seller");
            companyTypeRepository.save(companyType2);
        }

        if(saleTypeRepository.count() == 0){
            SaleType saleType1 = new SaleType("Wholesale");
            saleTypeRepository.save(saleType1);

            SaleType saleType2 = new SaleType("Retail");
            saleTypeRepository.save(saleType2);
        }

        if (regionRepository.count() == 0) {
            List<Region> regions = Arrays.asList(
                    new Region("Adygea", "AD"),
                    new Region("Altai", "AL"),
                    new Region("Altai Krai", "ALT"),
                    new Region("Amur", "AMU"),
                    new Region("Arkhangelsk", "ARK"),
                    new Region("Astrakhan", "AST"),
                    new Region("Belgorod", "BEL"),
                    new Region("Bryansk", "BRY"),
                    new Region("Chechen Republic", "CHE"),
                    new Region("Chelyabinsk", "CHEL"),
                    new Region("Chukotka Autonomous Okrug", "CHU"),
                    new Region("Chuvash Republic", "CHE"),
                    new Region("Dagestan", "DA"),
                    new Region("Ingushetia", "ING"),
                    new Region("Irkutsk", "IRK"),
                    new Region("Ivanovo", "IVA"),
                    new Region("Jewish Autonomous Oblast", "JAO"),
                    new Region("Kabardino-Balkar Republic", "KBR"),
                    new Region("Kaliningrad", "KGD"),
                    new Region("Kaluga", "KLU"),
                    new Region("Kamchatka Krai", "KAM"),
                    new Region("Karachay–Cherkess Republic", "KCH"),
                    new Region("Kemerovo", "KEM"),
                    new Region("Khabarovsk Krai", "KHA"),
                    new Region("Khakassia", "KK"),
                    new Region("Khanty–Mansi Autonomous Okrug – Yugra", "KHM"),
                    new Region("Kirov", "KIR"),
                    new Region("Komi", "KOM"),
                    new Region("Kostroma", "KOS"),
                    new Region("Krasnodar Krai", "KDA"),
                    new Region("Krasnoyarsk Krai", "KYA"),
                    new Region("Kurgan", "KGN"),
                    new Region("Kursk", "KRS"),
                    new Region("Leningrad", "LEN"),
                    new Region("Lipetsk", "LIP"),
                    new Region("Magadan", "MAG"),
                    new Region("Mari El", "MAR"),
                    new Region("Mordovia", "MOR"),
                    new Region("Moscow", "MOS"),
                    new Region("Moscow Oblast", "MOS"),
                    new Region("Murmansk", "MUR"),
                    new Region("Nenets Autonomous Okrug", "NGR"),
                    new Region("Nizhny Novgorod", "NGR"),
                    new Region("North Ossetia–Alania", "OSA"),
                    new Region("Novgorod", "NGR"),
                    new Region("Novosibirsk", "NVS"),
                    new Region("Omsk", "OMS"),
                    new Region("Orenburg", "ORE"),
                    new Region("Oryol", "ORY"),
                    new Region("Penza", "PEN"),
                    new Region("Perm Krai", "PER"),
                    new Region("Primorsky Krai", "PRI"),
                    new Region("Pskov", "PSK"),
                    new Region("Republic of Bashkortostan", "BA"),
                    new Region("Republic of Buryatia", "BU"),
                    new Region("Republic of Crimea", "CR"),
                    new Region("Republic of Dagestan", "DA"),
                    new Region("Republic of Ingushetia", "IN"),
                    new Region("Republic of Kalmykia", "KL"),
                    new Region("Republic of Karelia", "KR"),
                    new Region("Republic of Khakassia", "KK"),
                    new Region("Republic of Komi", "KO"),
                    new Region("Republic of Mari El", "ME"),
                    new Region("Republic of Mordovia", "MO"),
                    new Region("Republic of Sakha (Yakutia)", "SA"),
                    new Region("Republic of Tatarstan", "TA"),
                    new Region("Republic of Tyva", "TY"),
                    new Region("Republic of Udmurtia", "UD"),
                    new Region("Ryazan", "RYZ"),
                    new Region("Saint Petersburg", "SPE"),
                    new Region("Sakhalin", "SAK"),
                    new Region("Samara", "SAM"),
                    new Region("Saratov", "SAR"),
                    new Region("Smolensk", "SMO"),
                    new Region("Stavropol Krai", "STA"),
                    new Region("Sverdlovsk", "SVE"),
                    new Region("Tambov", "TAM"),
                    new Region("Tatarstan", "TA"),
                    new Region("Tula", "TUL"),
                    new Region("Tver", "TVE"),
                    new Region("Tyumen", "TYU"),
                    new Region("Tyva Republic", "TY"),
                    new Region("Udmurtia", "UD"),
                    new Region("Ulyanovsk", "ULY"),
                    new Region("Vladimir", "VLA"),
                    new Region("Volgograd", "VGG"),
                    new Region("Vologda", "VLG"),
                    new Region("Voronezh", "VOR"),
                    new Region("Yamalo-Nenets Autonomous Okrug", "YAR"),
                    new Region("Yaroslavl", "YAR")
            );
            regionRepository.saveAll(regions);
        }

    }
}