# 🎒 Akıllı Deprem Çantası Planlama Asistanı
### (Smart Earthquake Bag Planning Assistant)

![Java](https://img.shields.io/badge/Language-Java-orange.svg)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux%20%7C%20macOS-lightgrey.svg)
![License](https://img.shields.io/badge/License-MIT-blue.svg)
![Status](https://img.shields.io/badge/Status-Completed-success.svg)

**Akıllı Deprem Çantası Planlama Asistanı**, bireylerin ve ailelerin afet hazırlık süreçlerini bilimsel, sistematik ve bütçe dostu bir şekilde yönetmelerini sağlayan kapsamlı bir Java masaüstü uygulamasıdır. AFAD standartlarına ve risk seviyelerine uygun olarak geliştirilen bu proje, ağırlık optimizasyonu, maliyet analizi ve hayatta kalma puanlaması yaparak kullanıcıya rehberlik eder.

---

## 🎯 Projenin Amacı (Project Goal)

Bu projenin temel amacı, hayati öneme sahip olan deprem çantası hazırlama sürecini **dijitalleştirmek**, **hatasız hale getirmek** ve **farkındalık yaratmaktır**. Uygulama, kullanıcının bulunduğu bölgenin risk seviyesine (Yüksek/Orta/Düşük) ve aile bireylerinin özel durumlarına (Bebek, Yaşlı, Kronik Hasta vb.) göre dinamik ve hayat kurtarıcı listeler oluşturur.

---

## 🚀 Temel Özellikler (Key Features)

### 1. 👥 Gelişmiş Profil ve Risk Yönetimi
* **Risk Bazlı Planlama:** Bölgenin deprem riskine göre (Yüksek, Orta, Düşük) otomatik olarak su ve gıda stoklarını ayarlar.
* **Akıllı Kategoriler:** * **Bebek:** Bez, mama, ıslak mendil gibi özel ihtiyaçlar otomatik eklenir.
  * **Yaşlı:** İlaç kutusu, baston, yedek gözlük gibi gereksinimler otomatik eklenir.
  * **Kronik Hasta:** İlaçlar, reçete kopyaları ve yedek piller listeye dahil edilir.
* **Aile Modu:** Tüm aile bireylerini tek ekranda yönetme ve toplam aile yükünü hesaplama.

### 2. 🧠 Akıllı Analiz Algoritmaları
* **Hayatta Kalma Skoru (Survival Score):** Çantadaki kritik ürünleri (Su, Gıda, Işık, İlk Yardım, Düdük) analiz ederek 0-100 arası bir hazırlık puanı verir.
* **Akıllı Eksik Tespiti:** Çantada hayati öneme sahip eksikleri (Örn: "Düdük yok!", "Su yetersiz!") anlık olarak tespit eder ve kullanıcıyı uyarır.
* **Dinamik Kapasite Kontrolü:** Her profilin taşıyabileceği maksimum ağırlığı (Weight Limit) anlık olarak denetler.

### 3. 💰 Bütçe ve Maliyet Takibi
* Eklenen her ürünün tahmini maliyeti girilerek, bireysel ve toplam aile çantasının maliyeti hesaplanır. Afet hazırlığının bütçeye etkisi anlık olarak takip edilebilir.

### 4. 🌍 Çoklu Dil Desteği & UX
* **TR / EN Desteği:** Tek tıkla Türkçe ve İngilizce arayüz arasında geçiş (Para birimleri TL / $ olarak otomatik güncellenir).
* **Modern Arayüz (GUI):** Java Swing ile geliştirilmiş; renk kodlu ilerleme çubukları (Progress Bars), sekmeli yapı ve hata korumalı (Error-Safe) kullanıcı deneyimi.

### 5. 💾 Veri Yönetimi ve Raporlama
* **Geçmiş Kayıtlar:** Oluşturulan tüm bireysel ve aile planları tarihçesiyle birlikte saklanır.
* **TXT Dışa Aktarım:** Hazırlanan listeler `.txt` formatında bilgisayara indirilip çıktı alınabilir.

---

## 🛠️ Kullanılan Teknolojiler (Tech Stack)

* **Programlama Dili:** Java (JDK 8+)
* **Arayüz (GUI):** Java Swing (JFrame, JTabbedPane, JTable, Custom Renderers)
* **Veri Yapıları:** OOP (Nesne Yönelimli Programlama), Collections Framework (Lists, Maps, ArrayList), Exception Handling.
* **Versiyon Kontrol:** Git & GitHub.
* **IDE:** Eclipse IDE.

---

## 💻 Kurulum ve Kullanım (Installation & Usage)

Bu projeyi kendi bilgisayarınızda çalıştırmak için aşağıdaki adımları izleyin:

### Gereksinimler
* Bilgisayarınızda **Java Development Kit (JDK 8 veya üzeri)** kurulu olmalıdır.


### Adım 1: Projeyi Klonlayın
Terminal veya Komut Satırını açın ve şu komutu yazın:
```bash
git clone [https://github.com/DioBey7/Smart-Earthquake-Bag-Assistant.git](https://github.com/DioBey7/Smart-Earthquake-Bag-Assistant.git)
```

### Adım 2: IDE ile Açın
1. Eclipse veya IntelliJ IDEA'yı açın.

2. File > Open Project (veya Import Project) menüsünden indirdiğiniz klasörü seçin.

3. Projenin build path ayarlarının ve JDK sürümünün doğru olduğundan emin olun.

### Adım 3: Çalıştırın
1. Proje gezgininde src/com/beyza/earthquake/MainGUI.java dosyasını bulun.

2. Dosyaya sağ tıklayıp Run As > Java Application seçeneğine tıklayın.

3. Açılan pencerede dil seçimi yapıp (TR/EN) çantanızı hazırlamaya başlayabilirsiniz!


## 📸 Ekran Görüntüleri (Screenshots)

<div align="center">
  <img src="screenshots/mainmenu.png" width="700" alt="Uygulama Ana Ekranı" />
  <img src="screenshots/addingitems.png" width="700" alt="İtem Ekleme" />
  <img src="screenshots/listingfamilyitems.png" width="700" alt="Aile İtemlerini Listeleme" />
  <img src="screenshots/listingindividualbag" width="700" alt="Bireysel Çanta Menüsü" />
  <img src="screenshots/mainmenu2.png" width="700" alt="Uygulama Ana Ekranı 2" />
  <img src="screenshots/familymenu.png" width="700" alt="Aile Menüsü" />
</div>

## 📈 Geliştirme Süreci (Development Journey)
Bu proje, yazılım geliştirme yaşam döngüsünü (SDLC) simüle etmek amacıyla iteratif bir yaklaşımla geliştirilmiştir:

1. v1.0 (Konsol): Temel Java sınıfları (Item, Bag, FamilyMember) ve mantıksal katman oluşturuldu.

2. v2.0 (Temel GUI): Swing kütüphanesi ile kullanıcı arayüzü eklendi, veri girişi sağlandı.

3. v3.0 (Veri Yönetimi): Dosyalama işlemleri (File I/O) ve JSON/TXT formatında veri saklama özellikleri eklendi.

4. v4.0 (Algoritma Entegrasyonu): "Hayatta Kalma Skoru" ve "Akıllı Eksik Tespiti" algoritmaları sisteme entegre edildi.

5. Final (Risk & Aile Modu): Risk seviyelerine göre otomatik eşya atama, kategoriye özel ihtiyaçlar (Bebek/Yaşlı), bütçe takibi ve estetik (Flat Design) iyileştirmeleri ile proje tamamlandı.

## 🤝 İletişim
Geliştirici: Beyza Yazıcı

Proje ile ilgili öneri, katkı veya sorularınız için bana ulaşabilirsiniz:

- LinkedIn: www.linkedin.com/in/beyza-yazıcı-400183332
- Email: beyza04yazici2005@gmail.com
- GitHub: https://github.com/DioBey7

Bu proje, afet bilincini artırmak ve yazılım mühendisliği yetkinliklerini sergilemek amacıyla açık kaynak olarak geliştirilmiştir.
