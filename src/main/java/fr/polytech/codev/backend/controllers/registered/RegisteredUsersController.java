package fr.polytech.codev.backend.controllers.registered;

import fr.polytech.codev.backend.controllers.AbstractController;
import fr.polytech.codev.backend.entities.*;
import fr.polytech.codev.backend.exceptions.*;
import fr.polytech.codev.backend.forms.*;
import fr.polytech.codev.backend.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cryptowallet/registered/{tokenValue}/user")
public class RegisteredUsersController extends AbstractController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private FavoriteServices favoriteServices;

    @Autowired
    private CryptocurrencyServices cryptocurrencyServices;

    @Autowired
    private WalletServices walletServices;

    @Autowired
    private AssetServices assetServices;

    @Autowired
    private AlertServices alertServices;

    @Autowired
    private SettingServices settingServices;

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private LogServices logServices;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity get(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userServices.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable String tokenValue, @PathVariable int id, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);

        final User user = this.userServices.get(id);
        final UserForm userForm = deserialize(data, UserForm.class);
        userForm.setAdministrator(user.isAdministrator());
        userForm.setEnabled(user.isEnabled());

        return serializeSuccessResponse(this.userServices.update(id, userForm));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        this.userServices.delete(id);
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{id}/favorites", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity favorites(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userServices.get(id).getFavorites());
    }

    @RequestMapping(value = "/{id}/wallets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity wallets(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userServices.get(id).getWallets());
    }

    @RequestMapping(value = "/{id}/alerts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity alerts(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userServices.get(id).getAlerts());
    }

    @RequestMapping(value = "/{id}/settings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity settings(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userServices.get(id).getSettings());
    }

    @RequestMapping(value = "/{id}/tokens", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity tokens(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userServices.get(id).getTokens());
    }

    @RequestMapping(value = "/{id}/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity logs(@PathVariable String tokenValue, @PathVariable int id) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, id);
        return serializeSuccessResponse(this.userServices.get(id).getLogs());
    }

    @RequestMapping(value = "/{userId}/wallet/{walletId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity wallet(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int walletId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Wallet wallet = this.walletServices.get(walletId);
        assertEquals(user.getId(), wallet.getUser().getId());

        return serializeSuccessResponse(wallet);
    }

    @RequestMapping(value = "/{userId}/alert/{alertId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity alert(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int alertId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Alert alert = this.alertServices.get(alertId);
        assertEquals(user.getId(), alert.getUser().getId());

        return serializeSuccessResponse(alert);
    }

    @RequestMapping(value = "/{userId}/setting/{settingId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity setting(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int settingId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Setting setting = this.settingServices.get(settingId);
        assertEquals(user.getId(), setting.getUser().getId());

        return serializeSuccessResponse(setting);
    }

    @RequestMapping(value = "/{userId}/token/{tokenId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity token(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int tokenId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Token token = this.tokenServices.get(tokenId);
        assertEquals(user.getId(), token.getUser().getId());

        return serializeSuccessResponse(token);
    }

    @RequestMapping(value = "/{userId}/log/{logId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity log(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int logId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Log log = this.logServices.get(logId);
        assertEquals(user.getId(), log.getUser().getId());

        return serializeSuccessResponse(log);
    }

    @RequestMapping(value = "/{userId}/favorite/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addFavorite(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Cryptocurrency cryptocurrency = this.cryptocurrencyServices.get(cryptocurrencyId);

        return serializeSuccessResponse(this.favoriteServices.insert(user.getId(), cryptocurrency.getId(), new FavoriteForm()));
    }

    @RequestMapping(value = "/{userId}/wallet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addWallet(@PathVariable String tokenValue, @PathVariable int userId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);
        return serializeSuccessResponse(this.walletServices.insert(deserialize(data, WalletForm.class)));
    }

    @RequestMapping(value = "/{userId}/asset/wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addAsset(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int walletId, @PathVariable int cryptocurrencyId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Wallet wallet = this.walletServices.get(walletId);
        final Cryptocurrency cryptocurrency = this.cryptocurrencyServices.get(cryptocurrencyId);
        assertEquals(user.getId(), wallet.getUser().getId());

        try {
            // If the specified asset already exists : just update it
            final Asset asset = this.assetServices.get(wallet.getId(), cryptocurrency.getId());
            final AssetForm assetForm = deserialize(data, AssetForm.class);
            assetForm.setAmount(asset.getAmount().add(assetForm.getAmount()));
            assetForm.setPurchasePrice(asset.getPurchasePrice().add(assetForm.getPurchasePrice()));

            return serializeSuccessResponse(this.assetServices.update(wallet.getId(), cryptocurrency.getId(), assetForm));
        } catch (UnknownEntityException e) {
            // Else : just create it
            return serializeSuccessResponse(this.assetServices.insert(wallet.getId(), cryptocurrency.getId(), deserialize(data, AssetForm.class)));
        }
    }

    @RequestMapping(value = "/{userId}/alert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addAlert(@PathVariable String tokenValue, @PathVariable int userId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);
        return serializeSuccessResponse(this.alertServices.insert(deserialize(data, AlertForm.class)));
    }

    @RequestMapping(value = "/{userId}/setting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addSetting(@PathVariable String tokenValue, @PathVariable int userId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);
        return serializeSuccessResponse(this.settingServices.insert(deserialize(data, SettingForm.class)));
    }

    @RequestMapping(value = "/{userId}/token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addToken(@PathVariable String tokenValue, @PathVariable int userId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);
        return serializeSuccessResponse(this.tokenServices.insert(deserialize(data, TokenForm.class)));
    }

    @RequestMapping(value = "/{userId}/log", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addLog(@PathVariable String tokenValue, @PathVariable int userId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);
        return serializeSuccessResponse(this.logServices.insert(deserialize(data, LogForm.class)));
    }

    @RequestMapping(value = "/{userId}/wallet/{walletId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateWallet(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int walletId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);
        return serializeSuccessResponse(this.walletServices.update(walletId, deserialize(data, WalletForm.class)));
    }

    @RequestMapping(value = "/{userId}/asset/wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateAsset(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int walletId, @PathVariable int cryptocurrencyId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Wallet wallet = this.walletServices.get(walletId);
        final Cryptocurrency cryptocurrency = this.cryptocurrencyServices.get(cryptocurrencyId);
        assertEquals(user.getId(), wallet.getUser().getId());

        return serializeSuccessResponse(this.assetServices.update(wallet.getId(), cryptocurrency.getId(), deserialize(data, AssetForm.class)));
    }

    @RequestMapping(value = "/{userId}/alert/{alertId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateAlert(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int alertId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);
        return serializeSuccessResponse(this.alertServices.update(alertId, deserialize(data, AlertForm.class)));
    }

    @RequestMapping(value = "/{userId}/setting/{settingId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateSetting(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int settingId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);
        return serializeSuccessResponse(this.settingServices.update(settingId, deserialize(data, SettingForm.class)));
    }

    @RequestMapping(value = "/{userId}/token/{tokenId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateToken(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int tokenId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);
        return serializeSuccessResponse(this.tokenServices.update(tokenId, deserialize(data, TokenForm.class)));
    }

    @RequestMapping(value = "/{userId}/log/{logId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateLog(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int logId, @RequestBody String data) throws UnknownEntityException, InvalidEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);
        return serializeSuccessResponse(this.logServices.update(logId, deserialize(data, LogForm.class)));
    }

    @RequestMapping(value = "/{userId}/favorite/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteFavorite(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Cryptocurrency cryptocurrency = this.cryptocurrencyServices.get(cryptocurrencyId);

        this.favoriteServices.delete(user.getId(), cryptocurrency.getId());
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{userId}/wallet/{walletId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteWallet(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int walletId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Wallet wallet = this.walletServices.get(walletId);
        assertEquals(user.getId(), wallet.getUser().getId());

        this.walletServices.delete(wallet.getId());
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{userId}/asset/wallet/{walletId}/cryptocurrency/{cryptocurrencyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteAsset(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int walletId, @PathVariable int cryptocurrencyId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Wallet wallet = this.walletServices.get(walletId);
        final Cryptocurrency cryptocurrency = this.cryptocurrencyServices.get(cryptocurrencyId);
        assertEquals(user.getId(), wallet.getUser().getId());

        this.assetServices.delete(wallet.getId(), cryptocurrency.getId());
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{userId}/alert/{alertId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteAlert(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int alertId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Alert alert = this.alertServices.get(alertId);
        assertEquals(user.getId(), alert.getUser().getId());

        this.alertServices.delete(alert.getId());
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{userId}/setting/{settingId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteSetting(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int settingId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Setting setting = this.settingServices.get(settingId);
        assertEquals(user.getId(), setting.getUser().getId());

        this.settingServices.delete(setting.getId());
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{userId}/token/{tokenId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteToken(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int tokenId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Token token = this.tokenServices.get(tokenId);
        assertEquals(user.getId(), token.getUser().getId());

        this.tokenServices.delete(token.getId());
        return serializeSuccessResponse();
    }

    @RequestMapping(value = "/{userId}/log/{logId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteLog(@PathVariable String tokenValue, @PathVariable int userId, @PathVariable int logId) throws UnknownEntityException, InvalidTokenException, ExpiredTokenException, UnauthorizedUserException {
        assertUserIsUser(tokenValue, userId);

        final User user = this.userServices.get(userId);
        final Log log = this.logServices.get(logId);
        assertEquals(user.getId(), log.getUser().getId());

        this.logServices.delete(log.getId());
        return serializeSuccessResponse();
    }

    private void assertEquals(int userId, int entityUserId) throws UnauthorizedUserException {
        if (userId != entityUserId) {
            throw new UnauthorizedUserException();
        }
    }
}