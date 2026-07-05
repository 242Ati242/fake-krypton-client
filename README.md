# Fake Krypton Client (sadece görsel ClickGUI)

Bu proje, popüler "hile client"larını görsel olarak taklit eden ama **hiçbir
gerçek işlev içermeyen** bir Fabric mod'udur. Right Shift'e basınca ekranda
bir ClickGUI açılır; modüllere tıklamak sadece bir açma/kapama animasyonu
gösterir. ESP, KillAura, Fly, X-Ray, Nuker, Dupe Exploit vb. isimlerin
hepsi düz metindir — hiçbiri oyuncunun konumunu, sağlığını, paketlerini,
render pipeline'ını ya da sunucu bağlantısını etkilemez. İsmi kasıtlı
olarak "Fake" ile başlıyor.

## Jar dosyasını GitHub Actions ile üretme (bilgisayarına bir şey kurmadan)

1. https://github.com üzerinde ücretsiz bir hesap aç (yoksa).
2. Yeni, boş bir **repository** oluştur (public ya da private, fark etmez).
3. Bu klasördeki TÜM dosyaları ve klasörleri (özellikle `.github` klasörü
   dahil — gizli klasördür, dosya yöneticinde "gizli dosyaları göster"i aç)
   repo'ya yükle. GitHub web arayüzünde "Add file → Upload files" ile
   sürükle-bırak yeterli.
4. Yükleme bitince repo sayfasında üstteki **Actions** sekmesine tıkla.
5. "Build Fake Krypton Client" adlı workflow otomatik başlamış olmalı
   (başlamadıysa workflow'a tıklayıp "Run workflow" butonuna bas).
6. Birkaç dakika bekle, yeşil tik çıkınca workflow'a tıkla, en altta
   **Artifacts** bölümünden `fake-krypton-client-jar` dosyasını indir.
   İçinden çıkan `.jar` dosyası kullanıma hazır mod dosyandır.

Versiyon numaraları (`gradle.properties` içindeki `yarn_mappings`,
`loader_version`, `fabric_version`) 1.21.11 için güncel değilse build
hata verebilir; bu durumda https://fabricmc.net/develop adresinden
1.21.11 için güncel değerleri alıp `gradle.properties`'i düzenleyip
tekrar yükle.

## Oyun içinde kullanım

- **Right Shift**: ClickGUI'yi aç/kapat (ESC de kapatır).
- Kategori başlığına tıkla: kategoriyi genişlet/daralt, başlıktan sürükle:
  paneli taşı.
- Modül satırına tıkla: sadece görsel toggle (mor/gri anahtar).
- Modül satırındaki "+" işaretine tıkla: sahte slider/checkbox ayarlarını
  aç/kapat.

Kurulum için gerekenler (Fabric Loader + Fabric API + üretilen jar'ı
`.minecraft/mods` klasörüne koymak) değişmedi.

## Neden hiçbir şey "çalışmıyor"?

Çünkü kasıtlı olarak hiçbir mixin, event listener veya packet hook
eklenmedi. `Module` sınıfı sadece bir `boolean enabled` alanı tutar ve bu
değeri okuyan tek yer GUI'nin kendi çizim kodudur.
