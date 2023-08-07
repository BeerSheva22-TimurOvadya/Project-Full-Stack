package telran.spring;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import telran.spring.model.Advert;
import telran.spring.service.AdvertService;

@TestConfiguration
public class AdvertControllerTestConfiguration {
	public static int TEST_ID = 888888;

	@Bean
	public AdvertService advertService() {
		return new MockAdvertService();
	}

	class MockAdvertService implements AdvertService {

		@Override
		public Advert addAdvert(Advert advert) {
			return advert;
		}

		@Override
		public List<Advert> getAllAdverts() {
			return Arrays.asList(new Advert(TEST_ID, "test", "test", 100, null));
		}

		@Override
		public List<Advert> getAdvertsByCategory(String category) {
			return Arrays.asList(new Advert(TEST_ID, "test", category, 100, null));
		}

		@Override
		public List<Advert> getAdvertsByPrice(double maxPrice) {
			return Arrays.asList(new Advert(TEST_ID, "test", "test", maxPrice, null));
		}

		@Override
		public Advert editAdvert(int id, Advert advert) {
			return advert;
		}

		@Override
		public void deleteAdvert(int id) {
		}

		@Override
		public List<Advert> addAdverts(List<Advert> adverts) {
			return adverts;
		}

	}

}
