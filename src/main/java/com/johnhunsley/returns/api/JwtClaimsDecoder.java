package com.johnhunsley.returns.api;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>
 *     Decodes the JWT Auth0 access_token to discover the custom claims which are mapped against
 *     a prefix URI
 * </p>
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 06/11/2017
 *         Time : 13:25
 */
@Component
public class JwtClaimsDecoder {
    public final static String MEMBER_ID_KEY = "memberid";
    public final static String MEMBER_NAME_KEY = "nickname";

    @Value("${auth0.custom.claim.prefix}")
    private String customClaimPrefix;

    private String memberIdClaim;
    private String memberNameClaim;

    @PostConstruct
    public void init() {
        memberIdClaim = customClaimPrefix+MEMBER_ID_KEY;
        memberNameClaim = customClaimPrefix+MEMBER_NAME_KEY;
    }

    public String resolveMemberName(AuthenticationJsonWebToken token) throws Exception {
        return resolveClaim(token, memberNameClaim);
    }

    public String resolveMemberId(AuthenticationJsonWebToken token) throws Exception {
        return resolveClaim(token, memberIdClaim);
    }

    /**
     *
     * @param token
     * @param claim
     * @return
     * @throws Exception
     */
    private synchronized String resolveClaim(AuthenticationJsonWebToken token, final String claim) throws Exception {
        DecodedJWT decoded = (DecodedJWT)token.getDetails();
        final Claim obj = decoded.getClaim(claim);

        if(obj == null) throw new Exception("No custom org claim for user "+token.getPrincipal().toString());

        String claimStr =  obj.asString();
        return claimStr;
    }
}
